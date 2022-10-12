package io.regent.bookcruddemo.service.impl;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.exceptions.BookNotFoundException;
import io.regent.bookcruddemo.exceptions.WrongBookReferenceException;
import io.regent.bookcruddemo.repository.api.BookRepositoryApi;
import io.regent.bookcruddemo.service.api.BookServiceApi;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */

@RequiredArgsConstructor
public class BookServiceApiImpl implements BookServiceApi {

    private static final int DEFAULT = 9;
    private final BookRepositoryApi bookRepository;
    @Override
    public Book retrieveBook(final String bookReference) throws BookNotFoundException, WrongBookReferenceException {
        validateBookReference(bookReference);
        return bookRepository.retrieveBook(bookReference);
    }

    @Override
    public String getBookSummary(final String bookReference) throws BookNotFoundException, WrongBookReferenceException {
        validateBookReference(bookReference);
        StringBuilder bookSummary = new StringBuilder();
        Book storedBook = bookRepository.retrieveBook(bookReference);
        bookSummary.append("[").append(storedBook.getReference()).append("]").append(" ").append(storedBook.getTitle()).append(" - ");
        String [] reviewWords = storedBook.getReview().split(" ");
        final boolean checkLength = reviewWords.length <= 8;
        final int reviewWordsLength = checkLength ? reviewWords.length : DEFAULT;
            bookSummary.append(Arrays.stream(Arrays.copyOfRange(reviewWords, 0, reviewWordsLength ))
                    .reduce("", (partialString, value) -> partialString + " " + value)).append(checkLength ? "." : " ...");
        return bookSummary.toString();
    }

    private void validateBookReference(String bookReference) throws WrongBookReferenceException,
            BookNotFoundException {
        if(!bookReference.startsWith("BOOK-")){
            throw new WrongBookReferenceException(HttpStatus.BAD_REQUEST, "Book reference must begin with BOOK-");
        }

        if (!bookRepository.existsByReference(bookReference)){
            throw new BookNotFoundException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }
}
