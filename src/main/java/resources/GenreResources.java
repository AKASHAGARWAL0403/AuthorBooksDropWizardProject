package resources;

import core.Genre;
import db.GenreDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.constraints.Positive;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This is the Resource class for the Genre Entity.
 * It handles various request call for Genre
 * It handles findAll , createGenre functionality*/
@Path("/genre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreResources {
    private GenreDAO genreDAO;

    public GenreResources(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    /**
     * This is used to find a list of all Genre
     * @return Response with the list of genre added to it
     * */
    @GET
    @UnitOfWork
    public Response findAll(){
        List<Genre> genres = genreDAO.findAll();
        return Response.accepted(genres)
                .status(200).build();
    }

    /**
     * This is used to create a new Genre object
     * @param genre This is the genre object we need to add
     * @return Response with appropriate status*/
    @POST
    @UnitOfWork
    public Response createGenre(Genre genre){
        genre = genreDAO.create(genre);
        return Response.accepted(genre)
                .status(200).build();
    }
}
