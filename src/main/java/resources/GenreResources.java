package resources;

import core.Genre;
import db.GenreDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.constraints.Positive;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/genre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreResources {
    private GenreDAO genreDAO;

    public GenreResources(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    @GET
    @UnitOfWork
    public Response findAll(){
        List<Genre> genres = genreDAO.findAll();
        return Response.accepted(genres)
                .status(200).build();
    }

    @POST
    @UnitOfWork
    public Response createGenre(Genre genre){
        genre = genreDAO.create(genre);
        return Response.accepted(genre)
                .status(200).build();
    }
}
