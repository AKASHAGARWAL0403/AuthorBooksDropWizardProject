package core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name = "books")
@NamedQueries({
        @NamedQuery(name = "core.Book.findAll",
                query = "select b from Book b")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_name")
    @NotNull
    private String name;

    @Column(name = "date_of_publish")
    @NotNull
    private String dateOfPublish;

    @Column(name = "pages_count")
    @NotNull
    private int pagesCount;

    @Column(name = "part")
    @NotNull
    private int part;

    @Column(name = "edition")
    @NotNull
    private int edition;

    @OneToOne
    @JoinColumn(name = "previous_part_id" , nullable = true)
    private Book previousPart;

    @ManyToOne
    @JoinColumn(name = "genre_id" , nullable = true)
    private Genre genre;

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "author_books",
            joinColumns = { @JoinColumn(name = "books_id",
                    referencedColumnName = "id" , nullable = false , updatable = false)},
            inverseJoinColumns = { @JoinColumn(name = "author_id",
                    referencedColumnName = "id" , nullable = false , updatable = false)}
    )
    private Set<Author> author = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(String dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public Book getPreviousPart() {
        return previousPart;
    }

    public void setPreviousPart(Book previousPart) {
        this.previousPart = previousPart;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Author> getAuthor() {
        return author;
    }

    public void setAuthor(Set<Author> author) {
        this.author = author;
    }
}
