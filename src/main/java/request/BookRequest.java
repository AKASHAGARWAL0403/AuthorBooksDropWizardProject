package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Book;
import core.Genre;
import jdk.internal.jline.internal.Nullable;

/**
 * This is a request class which is Used to take
 * PUT request data for an Book Entity
 * */
public class BookRequest {

    @JsonProperty("name")
    @Nullable
    public String name;

    @JsonProperty("dateOfPublish")
    @Nullable
    public String dateOfPublish;

    @JsonProperty("pagesCount")
    @Nullable
    public Integer pagesCount;

    @JsonProperty("part")
    @Nullable
    public Integer part;

    @JsonProperty("edition")
    @Nullable
    public Integer edition;

    @JsonProperty("previousPart")
    @Nullable
    public Book previousPart;

    @JsonProperty("genre")
    @Nullable
    public Genre genre;

}

