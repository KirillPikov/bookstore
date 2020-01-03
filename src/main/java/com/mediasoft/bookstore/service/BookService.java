package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    /**
     * Получение книги по ID.
     * @param bookId ID книги.
     * @return
     */
    Book getBookById(Long bookId);

    /**
     * Получение страницы всех книг.
     * @param pageable настройка страницы.
     * @return
     */
    List<Book> getAllBooks(Pageable pageable);

    /**
     * Получение всех книг автора по его ID.
     * @param authorId ID автора.
     * @param pageable настройка страницы.
     * @return
     */
    List<Book> getAllBooksByAuthorId(Long authorId, Pageable pageable) throws EntityNotFoundException;

    /**
     * Получение всех книг издателя по его ID.
     * @param publisherId ID издателя.
     * @param pageable настройка страницы.
     * @return
     */
    List<Book> getAllBooksByPublisherId(Long publisherId, Pageable pageable) throws EntityNotFoundException;

    /**
     * Добавление новой книги.
     * @param book новая книга.
     */
    void addBook(Book book);

    /**
     * Изменение состояния киниги.
     * @param bookId ID кинги.
     * @param book новое состояние книги.
     */
    void updateBook(Long bookId, Book book);

    /**
     * Удаление книги по её ID.
     * @param bookId ID книги.
     */
    void deleteBook(Long bookId);
}