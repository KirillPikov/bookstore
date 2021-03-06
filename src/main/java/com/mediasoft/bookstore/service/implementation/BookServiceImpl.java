package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.AuthorRepository;
import com.mediasoft.bookstore.repository.BookRepository;
import com.mediasoft.bookstore.repository.PublisherRepository;
import com.mediasoft.bookstore.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final PublisherRepository publisherRepository;

    /**
     * Получение книги по ID.
     *
     * @param bookId ID книги.
     * @return
     */
    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Book with id = " + bookId + " - not found.");
                        }
                );
    }

    /**
     * Получение страницы всех книг.
     *
     * @param pageable настройка страницы.
     * @return
     */
    @Override
    public List<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Получение всех книг автора по его ID.
     *
     * @param authorId ID автора.
     * @param pageable настройка страницы.
     * @return
     */
    @Override
    public List<Book> getAllBooksByAuthorId(Long authorId, Pageable pageable) throws EntityNotFoundException {
        if(authorRepository.existsById(authorId)) {
            return bookRepository.getAllByAuthor_Id(authorId, pageable);
        } else {
            throw new EntityNotFoundException("Author with id = " + authorId + " - not found.");
        }
    }

    /**
     * Получение всех книг издателя по его ID.
     *
     * @param publisherId ID издателя.
     * @param pageable    настройка страницы.
     * @return
     */
    @Override
    public List<Book> getAllBooksByPublisherId(Long publisherId, Pageable pageable) throws EntityNotFoundException {
        if(publisherRepository.existsById(publisherId)) {
            return bookRepository.getAllByPublisher_Id(publisherId, pageable);
        } else {
            throw new EntityNotFoundException("Publisher with id = " + publisherId + " - not found.");
        }
    }

    /**
     * Добавление новой книги.
     *
     * @param book новая книга.
     */
    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    /**
     * Изменение состояния киниги.
     *
     * @param bookId ID кинги.
     * @param book   новое состояние книги.
     */
    @Override
    public void updateBook(Long bookId, Book book) {
        /* Выставляю ID книге, так как изначальное он не проинициализирован. */
        book.setId(bookId);
        /* Пробуем найти книгеу по переданному ID */
        bookRepository.findById(bookId)
                /* Если получилось найти, меняем её состояние и сохраняем */
                .ifPresentOrElse(
                        repoBook -> {
                            repoBook = book;
                            bookRepository.save(repoBook);
                        }, () -> {
                            /* Иначе выбрасываем исключение */
                            throw new EntityNotFoundException("Book with id = " + book.getId() + " - not found.");
                        }
                );
    }

    /**
     * Удаление книги по её ID.
     *
     * @param bookId ID книги.
     */
    @Override
    public void deleteBook(Long bookId) {
        /* Проверяем существование книги с таким ID */
        if(bookRepository.existsById(bookId)) {
            /* Если существует - удаляем */
            bookRepository.deleteById(bookId);
        } else {
            /* Иначе выбрасываем исключение */
            throw new EntityNotFoundException("Book with id = " + bookId + " - not found.");
        }
    }
}