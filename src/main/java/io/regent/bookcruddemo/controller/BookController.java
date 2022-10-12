package io.regent.bookcruddemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.regent.bookcruddemo.dtos.BookRequest;
import io.regent.bookcruddemo.entity.Book;
import io.regent.bookcruddemo.service.api.BookServiceApi;
import lombok.RequiredArgsConstructor;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final BookServiceApi bookService;


    @GetMapping("/retrieve")
    public ResponseEntity<Book> retrieveBookByReferenceNumber(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.retrieveBook(request.reference()));
    }

    @GetMapping("/getSummary")
    public ResponseEntity<String> getBookSummary(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.getBookSummary(request.reference()));
    }
}
