package core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jdk.internal.jline.internal.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the Entity class for the Author
 * It has all the fields needed for the Author
 * It also establishes the relationship needed With other Entity class
 * */
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name = "author")
@NamedQueries({
        @NamedQuery(name = "core.Author.findAll",
                query = "select a from Author a")
})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "age")
    @NotNull
    private int age;

    @Column(name = "address")
    @Nullable
    private String address;

    @Column(name = "contact_no")
    @Nullable
    private String contactNo;

    @Column(name = "email")
    @Nullable
    private String email;

    @Column(name = "gender")
    @NotNull
    private Gender gender;

    @Column(name = "books_published_count")
    @Nullable
    private int booksPublishedCount;

    @ManyToMany(mappedBy = "author" , fetch = FetchType.LAZY , cascade = CascadeType.PERSIST)
    private Set<Book> books = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getBooksPublishedCount() {
        return booksPublishedCount;
    }

    public void setBooksPublishedCount(int booksPublishedCount) {
        this.booksPublishedCount = booksPublishedCount;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
