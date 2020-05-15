package db;

import com.sun.tools.javac.jvm.Gen;
import core.Author;
import core.Book;
import core.Genre;
import io.dropwizard.hibernate.AbstractDAO;
import org.checkerframework.checker.nullness.Opt;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import request.BookRequest;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class BookDAO extends AbstractDAO<Book> {

    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public BookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Add a new findAll functionality for Book
     * @return List of all Book Objects
     * */
    public List<Book> findAll() {
        return list((Query<Book>) namedQuery("core.Book.findAll"));
    }

    /**
     * Add a new create functionality for Book
     * @return Book object
     * */
    public Book create(Book book){
        return persist(book);
    }

    /**
     * Add a new findById functionality for Book
     * @param id It for Book
     * @return Optional Book object
     * */
    public Optional<Book> findById(int id){
        return Optional.ofNullable(get(id));
    }

    /**
     * Add functionality of updating the authorSet for a Book
     * @param book Book object which we want to update
     * @param author Author object we want to add
     * @return Book object
     * */
    public Book addAuthor(Book book , Author author){
        Optional<Author> authorIfExist = Optional.ofNullable(
                currentSession().get(Author.class , author.getId())
        );
        if(!authorIfExist.isPresent())
            currentSession().save(author);
        book.getAuthor().add(author);
        currentSession().update(book);
        return book;
    }

    /**
     * Add functionality to update the Book object
     * @param previousBook Book we want to update
     * @param updateBook updates we want in Book
     * @return Optional updated Book object
     * */
    public Optional<Book> updateBook(Book previousBook , BookRequest updateBook){
        if(updateBook.name != null)
            previousBook.setName(updateBook.name);
        if(updateBook.dateOfPublish != null)
            previousBook.setDateOfPublish(updateBook.dateOfPublish);
        if(updateBook.edition != null)
            previousBook.setEdition(updateBook.edition);
        if(updateBook.pagesCount != null)
            previousBook.setPagesCount(updateBook.pagesCount);
        if(updateBook.part != null)
            previousBook.setPart(updateBook.part);
        if(updateBook.genre != null) {
            Optional<Genre> genre = Optional.ofNullable(
                    currentSession().get(Genre.class, updateBook.genre.getId())
            );
            if (!genre.isPresent())
                currentSession().save(updateBook.genre);
            previousBook.setGenre(updateBook.genre);
        }
        if(updateBook.previousPart != null) {
            Optional<Book> book = Optional.ofNullable(
                    currentSession().get(Book.class, updateBook.previousPart.getId())
            );
            if (!book.isPresent())
                return Optional.empty();
            previousBook.setPreviousPart(updateBook.previousPart);
        }
        currentSession().update(previousBook);
        return Optional.ofNullable(previousBook);
    }
}