//package resources;
//
//import core.Author;
//import db.AuthorDAO;
//import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
//import io.dropwizard.testing.junit.ResourceTestRule;
//import io.dropwizard.testing.junit5.ResourceExtension;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import request.AuthorRequest;
//
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import static org.assertj.core.api.Java6Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(DropwizardExtensionsSupport.class)
//public class AuthorResourceTest {
//    private static final AuthorDAO AUTHOR_DAO = mock(AuthorDAO.class);
//
//    public static final ResourceExtension RESOURCE_EXTENSION = ResourceExtension.builder()
//            .addResource(new AuthorResources(AUTHOR_DAO))
//            .build();
//
//    private ArgumentCaptor<Author> authorArgumentCaptor = ArgumentCaptor.forClass(Author.class);
//    private Author author;
//    private AuthorRequest authorRequest;
//
//    @BeforeEach
//    public void setUp(){
//        authorRequest = new AuthorRequest();
//        authorRequest.firstName = "Akash";
//        authorRequest.lastName = "Agarwal";
//        authorRequest.email = "akashagarwal@gmail.com";
//        authorRequest.contactNo = "9162728446";
//        authorRequest.gender = "MALE";
//        authorRequest.booksPublishedCount = 1;
//        authorRequest.age = 21;
//        authorRequest.address = "Jharia Dhanbad";
//        author = authorRequest.build().get();
//    }
//
//    @AfterEach
//    public void tearDown(){
//        reset(AUTHOR_DAO);
//    }
//
//    @Test
//    public void createAuthor(){
//        assertThat("akash").isEqualTo("akash");
//        when(AUTHOR_DAO.create(any(Author.class))).thenReturn(author);
//        final Response response = RESOURCE_EXTENSION.target("/authors")
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .post(Entity.entity(authorRequest , MediaType.APPLICATION_JSON_TYPE));
//        //assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
//    }
//}
