package resources;

import core.Author;
import core.Book;
import db.AuthorDAO;
import io.dropwizard.hibernate.UnitOfWork;
import request.AuthorRequest;
import request.BookRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * This is the Resource class for the Author Entity.
 * It handles various request call for Author
 * It handles findAll , findById , create , update , addBook , findBooks functionality*/
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResources {
    private AuthorDAO authorDAO;

    public AuthorResources(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    /**
     * This is used to find a list of all Author
     * @return Response with the list of author added to it
     * */
    @GET
    @UnitOfWork
    public Response findAll(){
        List<Author> authors = authorDAO.findAll();
        return Response.accepted(authors)
                .status(200).build();
    }

    /**
     * This is used to find Author based on id
     * @param id This is the author id which we need to find
     * @return Response with the appropriate status*/
    @Path("/{id}")
    @GET
    @UnitOfWork
    public Response findById(@PathParam("id") int id){
        Optional<Author> author = authorDAO.findById(id);
        if(author.isPresent()){
            return Response.accepted(author.get()).status(200).build();
        }else{
            return Response.notModified("No suck Author Exist")
                    .status(404).build();
        }
    }

    /**
     * This is used to update Author based on id
     * @param id This is the author id we need to update
     * @param updates This is the update we need to do
     * @return Response with appropriate status*/
    @Path("/{id}")
    @PUT
    @UnitOfWork
    public Response updateAuthor(@PathParam("id") int id , AuthorRequest updates){

        Map<String,String> validate = updates.validate();
        if(validate.size() != 0)
            return Response.accepted(validate)
                    .status(422).build();

        Optional<Author> previousAuthor = authorDAO.findById(id);
        if(!previousAuthor.isPresent())
            return Response.notModified("No such Author Exist")
                    .status(404).build();

        previousAuthor = authorDAO.updateAuthor(previousAuthor.get() , updates);
        if(previousAuthor.isPresent()){
            return Response.accepted(previousAuthor.get())
                    .status(200).build();
        }else {
            return Response.notModified("Data Provided is not correct")
                    .status(422).build();
        }
    }

    /**
     * This is used to create a new Author object
     * @param authorRequest This is the author object we need to add
     * @return Response with appropriate status*/
    @POST
    @UnitOfWork
    public Response createAuthor(AuthorRequest authorRequest) {

        Map<String,String> validate = authorRequest.validate();
        if(validate.size() != 0)
            return Response.accepted(validate)
                    .status(422).build();

        Optional<Author> author = authorRequest.build();
        if(!author.isPresent())
            return Response.accepted("Required field Not present")
                    .status(422).build();

        Author authorCreated = authorDAO.create(author.get());
        return Response.accepted(authorCreated)
                .status(200).build();
    }

    /**
     * This is used to add a Book for the Author
     * @param id This is id for Author for whom we need to add Book
     * @param book This is the book we need to add
     * @return Response with appropriate status*/
    @Path("/{id}/add-book")
    @POST
    @UnitOfWork
    public Response addBook(@PathParam("id") int id ,  Book book){
        Optional<Author> author = authorDAO.findById(id);
        if(author.isPresent()) {
            authorDAO.addBook(author.get() , book);
            return Response.accepted(author)
                    .status(200).build();
        }else {
            return Response.notModified("No such Author Exist")
                    .status(404).build();
        }
    }

    /**
     * This is used to find the list of book for a Author
     * @param id This is the id of Author for which we need to find Books
     * @return Response with appropriate status*/
    @Path("/{id}/books")
    @GET
    @UnitOfWork
    public Response findBooks(@PathParam("id") int id){
        Optional<Author> author = authorDAO.findById(id);
        if(author.isPresent()){
            Set<Book> books = authorDAO.findBooks(author.get());
            return Response.accepted(books).status(200).build();
        }else{
            return Response.notModified("No suck Author Exist")
                    .status(404).build();
        }
    }
}
