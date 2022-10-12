package io.regent.bookcruddemo.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.regent.bookcruddemo.entity.Book;

/**
 * Created by @author Ifeanyichukwu Otiwa
 *12/10/2022
 */

public interface BookRepositoryApiJPA extends JpaRepository<Book, String> {
    @Query("SELECT b FROM Book b WHERE b.reference = :bookReference")
    Book retrieveBook(String bookReference);

    boolean existsByReference(String bookReference);
}
