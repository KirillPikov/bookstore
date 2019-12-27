package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Находит все книги по ID автора.
     * @param authorId ID автора.
     * @return список книг.
     */
    List<Book> getAllByAuthor_Id(Long authorId, Pageable pageable);

    /**
     * Находит все книги по ID издателя.
     * @param publisherId ID издателя.
     * @return список книг.
     */
    List<Book> getAllByPublisher_Id(Long publisherId, Pageable pageable);
}