package core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This is the Entity class for the Genre
 * It has all the fields needed for the Genre
 * It also establishes the relationship needed With other Entity class
 * */
@Entity
@Table(name = "genre")
@NamedQueries({
        @NamedQuery(name = "core.Genre.findAll",
                query = "select g from Genre g")
})
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "genre_name")
    @NotNull
    private String name;

    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private Set<Book> booksSet = new HashSet<>();

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

    public Set<Book> getBooksSet() {
        return booksSet;
    }

    public void setBooksSet(Set<Book> booksSet) {
        this.booksSet = booksSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id == genre.id &&
                Objects.equals(name, genre.name) &&
                Objects.equals(booksSet, genre.booksSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, booksSet);
    }
}
