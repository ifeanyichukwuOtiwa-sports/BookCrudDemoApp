package io.regent.bookcruddemo.service.api;

import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.exceptions.BookNotFoundException;
import io.regent.bookcruddemo.exceptions.WrongBookReferenceException;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */

public interface BookServiceApi {
    Book retrieveBook(String bookReference) throws BookNotFoundException, WrongBookReferenceException;
    String getBookSummary(String bookReference) throws BookNotFoundException, WrongBookReferenceException;
}
