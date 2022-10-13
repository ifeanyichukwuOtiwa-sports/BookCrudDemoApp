package io.regent.bookcruddemo.service.api;

import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.exceptions.BookException;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */

public interface BookServiceApi {
    Book retrieveBook(String bookReference) throws BookException;
    String getBookSummary(String bookReference) throws BookException;
}
