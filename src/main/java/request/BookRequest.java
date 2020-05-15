package request;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.Book;
import core.Genre;
import jdk.internal.jline.internal.Nullable;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This is a request class which is Used to take
 * Book Class as request data for an Book Entity
 * It also check various validation
 * It also build a Book class for Object creation
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

    /**
     * Perform Validation for date
     * @return Boolean
     * */
    private boolean dateValidator(){
        if(dateOfPublish == null)
            return true;
        String[] dates = dateOfPublish.split("/");
        System.out.println(dates.toString());
        if(dates.length != 3)
            return false;
//        for(String date : dates){
//            if(!date.matches(".*[^0-9].*"))
//                return false;
//        }
        Integer[] datesInt = new Integer[3];
        for(int i = 0 ;i < 3; i++){
            datesInt[i] = Integer.parseInt(dates[i]);
        }
        System.out.println(datesInt.toString());
        if(datesInt[1] > 12)
            return false;
        if(datesInt[2] > 31)
            return false;
        return true;
    }

    /**
     * Validate all the validators build in
     * @return Map<String,String> It stores all the field that are not valid with respective comments
     * */
    public Map<String,String> validate(){
        Map<String,String> map = new HashMap<>();
        if(!dateValidator())
            map.put("Date" , "Date is not in the correct format. It should be yyyy/mm/dd");
        return map;
    }

    /**
     * Build a Book Object out of BookRequest Class
     * Performs Validation
     * Check all the required fields are present
     * @return Optional<Book> Object
     * */
    public Optional<Book> build(){
        if(name == null || dateOfPublish == null || pagesCount == null || edition == null || part == null)
            return Optional.empty();
        Book book = new Book();
        book.setName(name);
        book.setDateOfPublish(dateOfPublish);
        book.setPart(part);
        book.setPagesCount(pagesCount);
        book.setEdition(edition);
        book.setPreviousPart(previousPart);
        book.setGenre(genre);
        return Optional.ofNullable(book);
    }
}

