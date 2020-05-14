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
import java.util.Optional;
import java.util.Set;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResources {
    private AuthorDAO authorDAO;

    public AuthorResources(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @GET
    @UnitOfWork
    public Response findAll(){
        List<Author> authors = authorDAO.findAll();
        return Response.accepted(authors)
                .status(200).build();
    }

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

    @Path("/{id}")
    @PUT
    @UnitOfWork
    public Response updateAuthor(@PathParam("id") int id , AuthorRequest updates){
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

    @POST
    @UnitOfWork
    public Response createAuthor(Author author) {
        author = authorDAO.create(author);
        return Response.accepted(author)
                .status(200).build();
    }

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
