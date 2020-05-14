package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Gender;
import jdk.internal.jline.internal.Nullable;

public class AuthorRequest {

    @JsonProperty("firstName")
    @Nullable
    public String firstName;

    @JsonProperty("lastName")
    @Nullable
    public String lastName;

    @JsonProperty("age")
    @Nullable
    public Integer age;

    @JsonProperty("address")
    @Nullable
    public String address;

    @JsonProperty("contactNo")
    @Nullable
    public String contactNo;

    @JsonProperty("email")
    @Nullable
    public String email;

    @JsonProperty("gender")
    @Nullable
    public Gender gender;

    @JsonProperty("booksPublishedCount")
    @Nullable
    public Integer booksPublishedCount;

}