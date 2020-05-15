package db;

import com.sun.tools.javac.jvm.Gen;
import core.Genre;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class GenreDAO extends AbstractDAO<Genre> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    public GenreDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Add a new findAll functionality for Genre
     * @return List of all Genre Objects
     * */
    public List<Genre> findAll(){
        return list((Query<Genre>)namedQuery("core.Genre.findAll"));
    }

    /**
     * Add a new create functionality for Genre
     * @return Genre object
     * */
    public Genre create(Genre genre){
        return persist(genre);
    }
}
