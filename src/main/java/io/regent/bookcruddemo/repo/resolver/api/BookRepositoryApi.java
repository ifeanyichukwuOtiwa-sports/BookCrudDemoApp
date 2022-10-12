package io.regent.bookcruddemo.repo.resolver.api;

import io.regent.bookcruddemo.entity.Book;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */

public interface BookRepositoryApi {
    Book retrieveBook(String bookReference);

    boolean existsByReference(String bookReference);
}
