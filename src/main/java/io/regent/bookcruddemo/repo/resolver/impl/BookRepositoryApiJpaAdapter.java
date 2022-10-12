package io.regent.bookcruddemo.repo.resolver.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.repo.resolver.api.BookRepositoryApi;
import io.regent.bookcruddemo.repository.api.BookRepositoryApiJPA;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */
@RequiredArgsConstructor
@Service
@ConditionalOnProperty(name = "use.repository.stub.enable", havingValue = "false", matchIfMissing = true)
public class BookRepositoryApiJpaAdapter implements BookRepositoryApi {
    private final BookRepositoryApiJPA bookRepository;

    @Override
    public Book retrieveBook(final String bookReference) {
        return bookRepository.retrieveBook(bookReference);
    }

    @Override
    public boolean existsByReference(final String bookReference) {
        return bookRepository.existsByReference(bookReference);
    }
}
