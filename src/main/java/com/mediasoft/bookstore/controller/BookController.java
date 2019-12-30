package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.config.PaginationSettings;
import com.mediasoft.bookstore.config.PathSettings;
import com.mediasoft.bookstore.dto.BookDto;
import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.BookMapper;
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
@RequestMapping(PathSettings.BOOK_CONTROLLER_PATH)
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @GetMapping("/{" + PathSettings.BOOK_ID_PATH_VAR_NAME + "}")
    public ResponseEntity<BookDto> getBookById(@PathVariable(name = PathSettings.BOOK_ID_PATH_VAR_NAME) Long bookId)
            throws EntityNotFoundException {
        /* Получение книги из сервиса. */
        Book book = bookService.getBookById(bookId);
        /* Конвертирование в формат Dto */
        BookDto bookDto = bookMapper.toDto(book);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(
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
        /* Получение страницы всех книг */
        List<BookDto> bookDtos = bookService.getAllBooks(pageable)
                .stream()
                /* Конвертирование в Dto */
                .map(bookMapper::toDto)
                /* Сбор получившихся Dto в список */
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addBook(@RequestBody @Valid BookDto bookDto) {
        /* Конвертирование в формат Entity */
        Book book = bookMapper.toEntity(bookDto);
        /* Конвертирование в формат Dto */
        /* Добавление книги с помощью сервиса */
        bookService.addBook(book);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{" + PathSettings.BOOK_ID_PATH_VAR_NAME + "}", consumes = "application/json")
    public ResponseEntity updateBook(
            @PathVariable(name = PathSettings.BOOK_ID_PATH_VAR_NAME) Long customerId,
            @RequestBody @Valid BookDto bookDto
    ) throws EntityNotFoundException {
        /* Конвертирование в формат Entity */
        Book book = bookMapper.toEntity(bookDto);
        /* Обновление книги с помощью сервиса */
        bookService.updateBook(customerId, book);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{" + PathSettings.BOOK_ID_PATH_VAR_NAME + "}")
    public ResponseEntity deleteCustomer(@PathVariable(name = PathSettings.BOOK_ID_PATH_VAR_NAME) Long bookId)
            throws EntityNotFoundException {
        /* Удлаление книги с помощью сервиса */
        bookService.deleteBook(bookId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}