import core.Author;
import core.Book;
import core.Genre;
import db.AuthorDAO;
import db.BookDAO;
import db.GenreDAO;
import health.TemplateHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resources.AuthorResources;
import resources.BookResources;
import resources.GenreResources;
import resources.HelloWorldResources;

public class HelloWorldApplication extends Application<HelloWorldConfiguration>{

    private final HibernateBundle<HelloWorldConfiguration> hibernateBundle
            = new HibernateBundle<HelloWorldConfiguration>(
            Author.class,
            Book.class,
            Genre.class
    ) {

        @Override
        public DataSourceFactory getDataSourceFactory(
                HelloWorldConfiguration configuration
        ) {
            return configuration.getDataSourceFactory();
        }

    };

    public static void main(String args[]) throws Exception{
        new HelloWorldApplication().run(args);
    }

    public String getName(){
        return "Hello-World";
    }

    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap){
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {
        final AuthorDAO authorDAO
                = new AuthorDAO(hibernateBundle.getSessionFactory());

        final BookDAO booksDAO
                = new BookDAO(hibernateBundle.getSessionFactory());

        final GenreDAO genreDAO
                = new GenreDAO(hibernateBundle.getSessionFactory());

        final HelloWorldResources resource = new HelloWorldResources(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

        final TemplateHealthCheck templateHealthCheck =
                new TemplateHealthCheck(
                        configuration.getTemplate()
                );

        environment.jersey().register(resource);
        environment.healthChecks().register("template" , templateHealthCheck);
        environment.jersey().register(new AuthorResources(authorDAO));
        environment.jersey().register(new BookResources(booksDAO));
        environment.jersey().register(new GenreResources(genreDAO));
    }
}
