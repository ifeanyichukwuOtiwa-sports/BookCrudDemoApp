package io.regent.bookcruddemo.repo.resolver.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.regent.bookcruddemo.exceptions.BookNotFoundException;
import io.regent.bookcruddemo.exceptions.WrongBookReferenceException;
import io.regent.bookcruddemo.service.api.BookServiceApi;
import io.regent.bookcruddemo.service.impl.BookServiceApiImpl;

class BookRepositoryApiMapImplTest {

    private BookServiceApi bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceApiImpl(new BookRepositoryApiMapImpl());
    }

    @Test
    void retrieveBookSucceedsForBookInStore() {
        assertNotNull(bookService.retrieveBook("BOOK-GRUFF472"));
        String actualBookTitle = "Winnie The Pooh";
        String expectedBookTitle = bookService.retrieveBook("BOOK-POOH222").getTitle();
        assertEquals(expectedBookTitle, actualBookTitle);
    }

    /**
     * Wrong book reference exception thrown
     */
    @Test
    void retrieveBookFailsForInvalidBookReference() {
        String invalidBookReference = "INVALID_REFERENCE";
        WrongBookReferenceException exception = assertThrows(WrongBookReferenceException.class, () -> bookService.retrieveBook(invalidBookReference));
        String expectedErrorMessage = exception.getMessage();
        String actualErrorMessage = "400 BAD_REQUEST \"Book reference must begin with BOOK-\"";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    void retrieveBookFailsForBookNotInStore() {
        String referenceForBookNotInStore = "BOOK-NOT IN STORE";
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.retrieveBook(referenceForBookNotInStore));
        String expectedErrorMessage = exception.getMessage();
        String actualErrorMessage = "404 NOT_FOUND \"Book not found\"";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    void getBookSummarySuccessfulForBookInStore() {
        assertNotNull(bookService.getBookSummary("BOOK-GRUFF472"));
        String actualSummary = "[BOOK-GRUFF472] The Gruffalo -  A mouse taking a walk in the woods.";
        String expectedSummary = bookService.getBookSummary("BOOK-GRUFF472");
        assertEquals(expectedSummary, actualSummary);
    }

}