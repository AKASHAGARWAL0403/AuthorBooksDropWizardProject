package resources;

import core.Author;
import core.Book;
import db.BookDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.checkerframework.checker.nullness.Opt;
import request.BookRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This is the Resource class for the Book Entity.
 * It handles various request call for Book
 * It handles findAll , findById , create , update , addAuthor functionality*/
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResources {
    private BookDAO bookDAO;

    public BookResources(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    /**
     * This is used to find a list of all Book
     * @return Response with the list of book added to it
     * */
    @GET
    @UnitOfWork
    public Response findAll(){
        List<Book> books = bookDAO.findAll();
        return Response.accepted(books)
                .status(200).build();
    }

    /**
     * This is used to find Book based on id
     * @param id This is the book id which we need to find
     * @return Response with the appropriate status*/
    @Path("/{id}")
    @GET
    @UnitOfWork
    public Response findById(@PathParam("id") int id){
        Optional<Book> book = bookDAO.findById(id);
        if(book.isPresent()){
            return Response.accepted(book.get())
                    .status(200).build();
        }else{
            return Response.noContent()
                    .status(404).build();
        }
    }

    /**
     * This is used to update Book based on id
     * @param id This is the book id we need to update
     * @param updates This is the update we need to do
     * @return Response with appropriate status*/
    @Path("/{id}")
    @PUT
    @UnitOfWork
    public Response updateBook(@PathParam("id") int id , BookRequest updates){

        Map<String,String> validate = updates.validate();
        if(validate.size() != 0)
            return Response.accepted(validate)
                    .status(422).build();

        Optional<Book> previousBook = bookDAO.findById(id);
        if(!previousBook.isPresent())
            return Response.notModified("No such Book Exist")
                    .status(404).build();

        previousBook = bookDAO.updateBook(previousBook.get() , updates);
        if(previousBook.isPresent()){
            return Response.accepted(previousBook.get())
                    .status(200).build();
        }else {
            return Response.notModified("Data Provided is not correct")
                    .status(422).build();
        }
    }

    /**
     * This is used to create a new Book object
     * @param bookRequest This is the book object we need to add
     * @return Response with appropriate status*/
    @POST
    @UnitOfWork
    public Response createBook(BookRequest bookRequest){

        Map<String,String> validate = bookRequest.validate();
        if(validate.size() != 0)
            return Response.accepted(validate)
                    .status(422).build();

        Optional<Book> book = bookRequest.build();
        if(!book.isPresent())
            return Response.accepted("Required Field not present")
                    .status(422).build();

        book = bookDAO.create(book.get());
        if(!book.isPresent())
            return Response.accepted("Previous Part not an Object")
                    .status(422).build();
        return Response.accepted(book.get())
                .status(200).build();
    }

    /**
     * This is used to add a Author for a Book
     * @param id This is id of Book for whom we need to add a Author
     * @param author This is the author we need to add
     * @return Response with appropriate status*/
    @Path("/{id}/add-author")
    @POST
    @UnitOfWork
    public Response addAuthor(@PathParam("id") int id ,  Author author){
        Optional<Book> book = bookDAO.findById(id);
        if(book.isPresent()) {
            bookDAO.addAuthor(book.get() , author);
            return Response.accepted(book)
                    .status(200).build();
        }else {
            return Response.notModified("No such Book Exist")
                    .status(404).build();
        }
    }
}
