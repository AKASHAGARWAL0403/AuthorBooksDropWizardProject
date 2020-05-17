package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Author;
import core.Gender;
import jdk.internal.jline.internal.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * This is a request class which is Used to take
 * Author Class as request data for an Author Entity
 * It also check various validation
 * It also build a Author class for Object creation
 * */
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
    public String gender;

    @JsonProperty("booksPublishedCount")
    @Nullable
    public Integer booksPublishedCount;

    /**
     * Perform Age Validation.
     * Rejects Age above 100
     * @return Boolean
     * */
    private boolean ageValidator(){
        if(age  == null)
            return true;
        if(age > 100)
            return false;
        return true;
    }

    /**
     * Perform Gender Validation
     * @return Boolean
     * */
    private boolean genderValidator(){
        if(gender == null)
            return true;
        if(!gender.equalsIgnoreCase("MALE") || gender.equalsIgnoreCase("FEMALE"))
            return false;
        return true;
    }

    /**
     * Perform Contact No validator
     * Check if length of field is 10
     * Check if all character are digits
     * @return Boolean
     * */
    private boolean contactNoValidator(){
        if(contactNo == null)
            return true;
        if(contactNo.length() > 10)
            return false;
        if(!contactNo.matches(".*[^0-9].*"))
            return false;
        return true;
    }

    /**
     * Perform Email Validation
     * Check if @ is present
     * Check if .com is present
     * @return Boolean
     * */
    private boolean emailValidator(){
        if(email == null)
            return true;
        if(!email.contains("@"))
            return false;
        if(!email.contains(".com"))
            return false;
        return true;
    }

    /**
     * Perform all the built in validation
     * In a Waterfall manner
     * @return Map<String,String> It stores all the field that are not valid with respective comments
     * */
    public Map<String , String> validate(){
        Map<String,String> map = new HashMap<>();
        if(!ageValidator())
            map.put("Age" , "Age is Not Valid. It should be less than 100");
        if(!genderValidator())
            map.put("Gender" , "Gender is not valid. It should be Male or Female");
        if(!contactNoValidator())
            map.put("Contact No" , "Contact No is not Valid");
        if(!emailValidator())
            map.put("Email" , "Email is not valid");
        return map;
    }

    /**
     * Build a Author Object out of AuthorRequest Class
     * Performs Validation
     * Check all the required fields are present
     * @return Optional<Author> Object
     * */
    public Optional<Author> build(){
        if(firstName == null || lastName == null || age == null || gender == null)
            return Optional.empty();
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setGender(Gender.valueOf(gender.toUpperCase()));
        author.setAge(age);
        author.setEmail(email);
        author.setContactNo(contactNo);
        author.setBooksPublishedCount(booksPublishedCount);
        author.setAddress(address);
        return Optional.ofNullable(author);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorRequest that = (AuthorRequest) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(age, that.age) &&
                Objects.equals(address, that.address) &&
                Objects.equals(contactNo, that.contactNo) &&
                Objects.equals(email, that.email) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(booksPublishedCount, that.booksPublishedCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, address, contactNo, email, gender, booksPublishedCount);
    }
}