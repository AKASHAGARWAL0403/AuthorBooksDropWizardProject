package request;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sun.tools.javac.jvm.Gen;
import core.Book;
import core.Genre;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BookRequestTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception{
        final Genre genre = new Genre();
        genre.setName("Suspense");
        final BookRequest bookRequest = new BookRequest();
        bookRequest.name = "Harry Potter";
        bookRequest.dateOfPublish = "2012/02/21";
        bookRequest.pagesCount = 200;
        bookRequest.part = 1;
        bookRequest.edition = 1;
        bookRequest.genre = genre;
        bookRequest.previousPart = null;

        final String excepted = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/BookFixtures.json") , BookRequest.class)
        );

        assertThat(MAPPER.writeValueAsString(bookRequest)).isEqualTo(excepted);
    }
}
