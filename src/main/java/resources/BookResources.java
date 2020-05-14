package resources;

import core.Author;
import core.Book;
import db.BookDAO;
import io.dropwizard.hibernate.UnitOfWork;
import request.BookRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResources {
    private BookDAO bookDAO;

    public BookResources(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GET
    @UnitOfWork
    public Response findAll(){
        List<Book> books = bookDAO.findAll();
        return Response.accepted(books)
                .status(200).build();
    }

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

    @Path("/{id}")
    @PUT
    @UnitOfWork
    public Response updateBook(@PathParam("id") int id , BookRequest updates){
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

    @POST
    @UnitOfWork
    public Response createBook(Book book){
        book = bookDAO.create(book);
        return Response.accepted(book)
                .status(200).build();
    }

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
