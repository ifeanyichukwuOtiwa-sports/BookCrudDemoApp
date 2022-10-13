package io.regent.bookcruddemo.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void testHashCodeAndEquals() {
        Book book = new Book("reference", "title", "review");
        Book secondBook = new Book("reference", "title", "review");

        assertAll(
                () -> assertThat(book).hasSameHashCodeAs(secondBook),
                () -> assertThat(book).isEqualByComparingTo(secondBook),
                () -> assertThat(book).usingRecursiveComparison().isEqualTo(secondBook),
                () -> assertEquals(book, secondBook)
        );
    }

}