package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.Author;
import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.AuthorRepository;
import com.mediasoft.bookstore.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Получение автора по его ID.
     *
     * @param authorId ID автора, которого хотим получить.
     * @return Автора с указанным ID.
     * @throws EntityNotFoundException
     */
    @Override
    public Author getAuthor(Long authorId) throws EntityNotFoundException {
        return authorRepository.findById(authorId)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Author with id = " + authorId + " - not found.");
                        }
                );
    }

    /**
     * Получние списка всех авторов.
     *
     * @param pageable настройка страницы.
     * @return
     */
    @Override
    public List<Author> getAuthorsPage(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    /**
     * Добавление нового автора.
     *
     * @param author новый автор.
     * @return нового автора.
     */
    @Override
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    /**
     * Изменение состояние автора.
     *
     * @param authorId ID автора.
     * @param author   новое состояние автора.
     * @throws EntityNotFoundException
     */
    @Override
    public void updateAuthor(Long authorId, Author author) throws EntityNotFoundException {
        authorRepository.findById(authorId)
                .ifPresentOrElse(
                        repoAuthor -> {
                            List<Book> books = repoAuthor.getBooks();
                            repoAuthor = author;
                            repoAuthor.setId(authorId);
                            repoAuthor.setBooks(books);
                            authorRepository.save(repoAuthor);
                        }, () -> {
                                throw new EntityNotFoundException("Author with id = " + author.getId() + " - not found.");
                            }
                );
    }

    /**
     * Удаление автора по ID.
     *
     * @param authorId ID автора.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteAuthor(Long authorId) throws EntityNotFoundException {
        /* Проверяем существование автора с таким ID */
        if(authorRepository.existsById(authorId)) {
            /* Если существует - удаляем */
            authorRepository.deleteById(authorId);
        } else {
            /* Иначе выбрасываем исключение */
            throw new EntityNotFoundException("Author with id = " + authorId + " - not found.");
        }
    }
}