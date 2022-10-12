package io.regent.bookcruddemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.CompareToBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Comparable<Book> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 300)
    private String reference;
    @Column(length = 250, nullable = false)
    private String title;
    @Column(length = 700, columnDefinition = "TEXT")
    private String review;


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Book book = (Book) o;

        if (getReference() != null ? !getReference().equals(book.getReference()) : book.getReference() != null) return false;
        if (getTitle() != null ? !getTitle().equals(book.getTitle()) : book.getTitle() != null) return false;
        return getReview() != null ? getReview().equals(book.getReview()) : book.getReview() == null;
    }

    @Override
    public int hashCode() {
        int result = getReference() != null ? getReference().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(final Book other) {

        return new CompareToBuilder()
                .append(this.getTitle(), other.getTitle())
                .append(this.getId(), other.getId())
                .append(this.getReference(), other.getReference())
                .toComparison();
    }
}
