package io.regent.bookcruddemo.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.exceptions.BookNotFoundException;
import io.regent.bookcruddemo.exceptions.WrongBookReferenceException;
import io.regent.bookcruddemo.repo.resolver.api.BookRepositoryApi;
import io.regent.bookcruddemo.repo.resolver.impl.BookRepositoryApiJpaAdapter;
import io.regent.bookcruddemo.repository.api.BookRepositoryApiJPA;
import io.regent.bookcruddemo.service.api.BookServiceApi;


@ExtendWith(MockitoExtension.class)
class BookServiceApiImplTest {
    private static final String BOOK_REFERENCE_PREFIX = "BOOK-";
    private static final String THE_GRUFFALO_REFERENCE = BOOK_REFERENCE_PREFIX + "GRUFF472";
    private static final String WINNIE_THE_POOH_REFERENCE = BOOK_REFERENCE_PREFIX + "POOH222";

    @Mock
    private BookRepositoryApiJPA bookRepository;



    private BookServiceApi serviceUnderTest;

    @BeforeEach
    void setup() {
        BookRepositoryApi repositoryApiImpl = new BookRepositoryApiJpaAdapter(bookRepository);
        serviceUnderTest = new BookServiceApiImpl(repositoryApiImpl);
    }



    @Test
    void testRetrieveBook()  {
        final String bookReference = THE_GRUFFALO_REFERENCE;
        Book book = new Book(THE_GRUFFALO_REFERENCE, "The Gruffalo", "A mouse taking a walk in the woods");
        when(bookRepository.retrieveBook(bookReference)).thenReturn(book);
        when(bookRepository.existsByReference(bookReference)).thenReturn(true);
        Book retrieveBook = serviceUnderTest.retrieveBook(bookReference);
        verify(bookRepository, atLeast(1)).retrieveBook(bookReference);
        assertAll(
                () -> assertThat(retrieveBook).usingRecursiveComparison().isEqualTo(book),
                () -> assertThat(retrieveBook.getTitle()).isEqualTo("The Gruffalo")
        );
    }


    @Test
    void retrieveBookFailsForInvalidBookReference() {
        String invalidBookReference = "INVALID_REFERENCE";
        WrongBookReferenceException exception = assertThrows(
                WrongBookReferenceException.class,
                () -> serviceUnderTest.retrieveBook(invalidBookReference)
        );
        String expectedErrorMessage = exception.getMessage();
        String actualErrorMessage = "400 BAD_REQUEST \"Book reference must begin with BOOK-\"";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    void retrieveBookFailsForBookNotInStore() {
        String referenceForBookNotInStore = "BOOK-NOT IN STORE";
        when(bookRepository.existsByReference(referenceForBookNotInStore)).thenReturn(false);
        BookNotFoundException exception = assertThrows(
                BookNotFoundException.class,
                () -> serviceUnderTest.retrieveBook(referenceForBookNotInStore)
        );
        String expectedErrorMessage = exception.getMessage();
        String actualErrorMessage = "404 NOT_FOUND \"Book not found\"";
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }


    @Test
    void getBookSummarySuccessfulForBookInStore() throws WrongBookReferenceException, BookNotFoundException {
        final Book winnieBook = new Book(WINNIE_THE_POOH_REFERENCE, "Winnie The Pooh", "In this first volume we meet all the friends from the Hundred Acre Wood.");
        when(bookRepository.retrieveBook(WINNIE_THE_POOH_REFERENCE)).thenReturn(winnieBook);
        when(bookRepository.existsByReference(WINNIE_THE_POOH_REFERENCE)).thenReturn(true);
        String actualSummary = "[BOOK-POOH222] Winnie The Pooh -  In this first volume we meet all the friends ...";
        final String expectedBookSummary = serviceUnderTest.getBookSummary("BOOK-POOH222");
        assertAll(
                () -> assertNotNull(expectedBookSummary),
                () -> assertEquals(expectedBookSummary, actualSummary)
        );
    }

}