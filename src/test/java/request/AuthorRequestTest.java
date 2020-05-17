package request;

import static io.dropwizard.testing.FixtureHelpers.*;
import static org.assertj.core.api.Assertions.assertThat;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AuthorRequestTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception{
        final AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.firstName = "Akash";
        authorRequest.lastName = "Agarwal";
        authorRequest.email = "akashagarwal@gmail.com";
        authorRequest.contactNo = "9162728446";
        authorRequest.gender = "MALE";
        authorRequest.booksPublishedCount = 1;
        authorRequest.age = 21;
        authorRequest.address = "Jharia Dhanbad";

        final String excepted = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/AuthorFixtures.json") , AuthorRequest.class)
        );

        assertThat(MAPPER.writeValueAsString(authorRequest)).isEqualTo(excepted);
    }

    @Test
    public void deserializesFromJSON() throws Exception{
        final AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.firstName = "Akash";
        authorRequest.lastName = "Agarwal";
        authorRequest.email = "akashagarwal@gmail.com";
        authorRequest.contactNo = "9162728446";
        authorRequest.gender = "MALE";
        authorRequest.booksPublishedCount = 1;
        authorRequest.age = 21;
        authorRequest.address = "Jharia Dhanbad";

        assertThat(MAPPER.readValue(fixture("fixtures/AuthorFixtures.json"), AuthorRequest.class))
                .isEqualTo(authorRequest);
    }
}
