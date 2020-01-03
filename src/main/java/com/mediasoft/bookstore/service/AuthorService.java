package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.Author;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    /**
     * Получение автора по его ID.
     * @param authorId ID автора, которого хотим получить.
     * @return Автора с указанным ID.
     * @throws EntityNotFoundException
     */
    Author getAuthor(Long authorId) throws EntityNotFoundException;

    /**
     * Получние списка всех авторов.
     * @param pageable настройка страницы.
     * @return
     */
    List<Author> getAuthorsPage(Pageable pageable);

    /**
     * Добавление нового автора.
     * @param author новый автор.
     */
    void addAuthor(Author author);

    /**
     * Изменение состояние автора.
     * @param authorId ID автора.
     * @param author новое состояние автора.
     * @throws EntityNotFoundException
     */
    void updateAuthor(Long authorId, Author author) throws EntityNotFoundException;

    /**
     * Удаление автора по ID.
     * @param authorId ID автора.
     * @throws EntityNotFoundException
     */
    void deleteAuthor(Long authorId) throws EntityNotFoundException;
}