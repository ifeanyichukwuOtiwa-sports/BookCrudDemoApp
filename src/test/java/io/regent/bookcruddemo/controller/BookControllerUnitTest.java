package io.regent.bookcruddemo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.regent.bookcruddemo.dtos.BookRequest;
import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.service.api.BookServiceApi;

@ExtendWith(MockitoExtension.class)
class BookControllerUnitTest {

    @Mock
    private BookServiceApi bookServiceApi;

    @InjectMocks
    private BookController bookController;

    @Test
    void retrieveBookByReferenceNumber() {
        final String reference = "Winnie The Pooh";
        final BookRequest request = new BookRequest(reference);
        final Book bookValue = new Book();
        when(bookServiceApi.retrieveBook(reference)).thenReturn(bookValue);
        final ResponseEntity<Book> bookResponseEntity = bookController.retrieveBookByReferenceNumber(request);

        verifyResponse(bookResponseEntity, bookValue);
    }

    private static <T> void verifyResponse(final ResponseEntity<T> responseEntity, T value) {

        assertAll(
                () -> assertThat(responseEntity.getBody()).isEqualTo(value),
                () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        );
    }

    @Test
    void getBookSummary() {
        final String reference = "reference";
        final BookRequest request = new BookRequest(reference);
        final String summary = "test, summary";

        when(bookServiceApi.getBookSummary(reference)).thenReturn(summary);
        final ResponseEntity<String> bookSummary = bookController.getBookSummary(request);
        verifyResponse(bookSummary, summary);

    }
}