package io.regent.bookcruddemo.repository.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.repository.api.BookRepositoryApi;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */

@Repository
@ConditionalOnProperty(name ="enable", prefix = "use.repository.stub", havingValue = "true", matchIfMissing = false)
public class BookRepositoryApiMapImpl implements BookRepositoryApi {

    private static final String BOOK_REFERENCE_PREFIX = "BOOK-";
    private static final String THE_GRUFFALO_REFERENCE = BOOK_REFERENCE_PREFIX + "GRUFF472";
    private static final String WINNIE_THE_POOH_REFERENCE = BOOK_REFERENCE_PREFIX + "POOH222";
    private static final String THE_WIND_IN_THE_WILLOWS_REFERENCE = BOOK_REFERENCE_PREFIX + "WILL987";

    private static final Map<String, Book> books;

    static {
        books = new HashMap<>();
        books.put(THE_GRUFFALO_REFERENCE, new Book(THE_GRUFFALO_REFERENCE, "The Gruffalo", "A mouse taking a walk in the woods"));
        books.put(WINNIE_THE_POOH_REFERENCE, new Book(WINNIE_THE_POOH_REFERENCE, "Winnie The Pooh", "In this first volume we meet all the friends from the Hundred Acre Wood."));
        books.put(THE_WIND_IN_THE_WILLOWS_REFERENCE, new Book(THE_WIND_IN_THE_WILLOWS_REFERENCE, "The Wind In The Willows",
                """
                    With the arrival of spring and fine weather outside, the good-natured Mole loses patience with spring cleaning.\040
                    He flees his underground home, emerging to take in the air and ends up at the river, which he has never seen before.\040
                    Here he meets Rat (a water vole), who at this time of year spends all his days in, on and close by the river.\040
                    Rat takes Mole for a ride in his rowing boat. They get along well and spend many more days boating, with Rat teaching Mole the ways of the river.
                    """));
    }
    @Override
    public Book retrieveBook(final String bookReference) {
        return books.get(bookReference);
    }

    @Override
    public boolean existsByReference(final String bookReference) {
        return books.containsKey(bookReference);
    }
}
