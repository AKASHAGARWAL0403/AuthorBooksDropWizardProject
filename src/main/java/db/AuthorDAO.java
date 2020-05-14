package db;

import core.Author;
import core.Book;
import core.Genre;
import io.dropwizard.hibernate.AbstractDAO;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import request.AuthorRequest;
import request.BookRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AuthorDAO extends AbstractDAO<Author> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public AuthorDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Author> findAll(){
        return list((Query<Author>)namedQuery("core.Author.findAll"));
    }

    public Optional<Author> findById(int id){
        return Optional.ofNullable(get(id));
    }

    public Author create(Author author){
        return persist(author);
    }

    public Author addBook(Author author , Book book){
        Optional<Book> bookIfExist = Optional.ofNullable(
                currentSession().get(Book.class , book.getId())
        );
        if(!bookIfExist.isPresent())
            currentSession().save(book);
        author.getBooks().add(book);
        currentSession().update(author);
        return author;
    }

    public Set<Book> findBooks(Author author){
        return author.getBooks();
    }

    public Optional<Author> updateAuthor(Author previousAuthor , AuthorRequest updateAuthor){
        if(updateAuthor.firstName != null)
            previousAuthor.setFirstName(updateAuthor.firstName);
        if(updateAuthor.lastName != null)
            previousAuthor.setLastName(updateAuthor.lastName);
        if(updateAuthor.address != null)
            previousAuthor.setAddress(updateAuthor.address);
        if(updateAuthor.age != null)
            previousAuthor.setAge(updateAuthor.age);
        if(updateAuthor.booksPublishedCount != null)
            previousAuthor.setBooksPublishedCount(updateAuthor.booksPublishedCount);
        if(updateAuthor.contactNo != null) {
            previousAuthor.setContactNo(updateAuthor.contactNo);
        }
        if(updateAuthor.email != null) {
            previousAuthor.setEmail(updateAuthor.email);
        }
        if(updateAuthor.gender != null){
            previousAuthor.setGender(updateAuthor.gender);
        }
        currentSession().update(previousAuthor);
        return Optional.ofNullable(previousAuthor);
    }
}