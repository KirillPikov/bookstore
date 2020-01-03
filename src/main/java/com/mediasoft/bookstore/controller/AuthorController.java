package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.config.PaginationSettings;
import com.mediasoft.bookstore.config.PathSettings;
import com.mediasoft.bookstore.dto.AuthorDto;
import com.mediasoft.bookstore.dto.BookDto;
import com.mediasoft.bookstore.entity.Author;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.AuthorMapper;
import com.mediasoft.bookstore.mapper.BookMapper;
import com.mediasoft.bookstore.service.AuthorService;
import com.mediasoft.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping(PathSettings.AUTHOR_CONTROLLER_PATH)
public class AuthorController {

    //-------------------------< Services >-------------------------//
    private final AuthorService authorService;

    private final BookService bookService;

    //-------------------------< Mappers >--------------------------//
    private final AuthorMapper authorMapper;

    private final BookMapper bookMapper;

    @GetMapping("/{" + PathSettings.AUTHOR_ID_PATH_VAR_NAME + "}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable(name = PathSettings.AUTHOR_ID_PATH_VAR_NAME) Long authorId)
            throws EntityNotFoundException {
        /* Получение автора из сервиса. */
        Author author = authorService.getAuthor(authorId);
        /* Конвертирование в формат Dto */
        AuthorDto authorDto = authorMapper.toDto(author);
        return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAuthorList(
            @RequestParam(name = PathSettings.PAGE_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_PAGE) Integer page,
            @RequestParam(name = PathSettings.COUNT_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_ELEMENTS_COUNT) Integer count,
            @RequestParam(name = PathSettings.SORTING_FIELD_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_SORTING_FIELD) String sortingField,
            @RequestParam(name = PathSettings.SORTING_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_SORTING) String sorting
    ) {
        Sort sort;
        if(sorting.equals("ASC")) {
            sort = Sort.by(sortingField).ascending();
        } else {
            sort = Sort.by(sortingField).descending();
        }
        Pageable pageable = PageRequest.of(page, count, sort);
        /* Конвертирование в формат Dto полученных из сервиса всех авторов */
        List<AuthorDto> authorDtos = authorService.getAuthorsPage(pageable)
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorDtos, HttpStatus.OK);
    }

    @GetMapping("/{" + PathSettings.AUTHOR_ID_PATH_VAR_NAME + "}/" + PathSettings.BOOK_CONTROLLER_PATH)
    public ResponseEntity<List<BookDto>> getAuthorBooksList(
            @PathVariable(name = PathSettings.AUTHOR_ID_PATH_VAR_NAME) Long authorId,
            @RequestParam(name = PathSettings.PAGE_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_PAGE) Integer page,
            @RequestParam(name = PathSettings.COUNT_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_ELEMENTS_COUNT) Integer count,
            @RequestParam(name = PathSettings.SORTING_FIELD_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_SORTING_FIELD) String sortingField,
            @RequestParam(name = PathSettings.SORTING_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_SORTING) String sorting
    ) throws EntityNotFoundException {
        Sort sort;
        if(sorting.equals("ASC")) {
            sort = Sort.by(sortingField).ascending();
        } else {
            sort = Sort.by(sortingField).descending();
        }
        Pageable pageable = PageRequest.of(page, count, sort);
        /* Конвертирование в формат Dto полученных книг */
        List<BookDto> bookDtos = bookService.getAllBooksByAuthorId(authorId, pageable)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addAuthor(@RequestBody @Valid AuthorDto authorDto) {
        /* Конвертирование в формат Entity */
        Author author = authorMapper.toEntity(authorDto);
        /* Добавление автора с помощью сервиса */
        authorService.addAuthor(author);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{" + PathSettings.AUTHOR_ID_PATH_VAR_NAME + "}", consumes = "application/json")
    public ResponseEntity updateAuthor(
            @PathVariable(name = PathSettings.AUTHOR_ID_PATH_VAR_NAME) Long authorId,
            @RequestBody @Valid AuthorDto authorDto
    ) throws EntityNotFoundException {
        /* Конвертирование в формат Entity */
        Author author = authorMapper.toEntity(authorDto);
        /* Обновление автора с помощью сервиса */
        authorService.updateAuthor(authorId, author);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{" + PathSettings.AUTHOR_ID_PATH_VAR_NAME + "}")
    public ResponseEntity deleteAuthor(@PathVariable(name = PathSettings.AUTHOR_ID_PATH_VAR_NAME) Long authorId)
            throws EntityNotFoundException {
        /* Удлаление автора с помощью сервиса */
        authorService.deleteAuthor(authorId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}