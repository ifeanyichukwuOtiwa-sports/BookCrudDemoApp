package io.regent.bookcruddemo.repository.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.repository.api.BookRepositoryApi;

/**
 * Created by @author Ifeanyichukwu Otiwa
 *12/10/2022
 */

@Repository
@ConditionalOnProperty(name ="enable", prefix = "use.repository.stub", havingValue = "false", matchIfMissing = true)
public interface BookRepositoryApiJPA extends BookRepositoryApi, JpaRepository<Book, String> {
    @Query("SELECT b FROM Book b WHERE b.reference = :reference")
    Book retrieveBook(String bookReference);

    boolean existsByReference(String bookReference);
}
