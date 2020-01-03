package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.config.PaginationSettings;
import com.mediasoft.bookstore.config.PathSettings;
import com.mediasoft.bookstore.dto.BookDto;
import com.mediasoft.bookstore.dto.PublisherDto;
import com.mediasoft.bookstore.entity.Publisher;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.BookMapper;
import com.mediasoft.bookstore.mapper.PublisherMapper;
import com.mediasoft.bookstore.service.BookService;
import com.mediasoft.bookstore.service.PublisherService;
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
@RequestMapping(PathSettings.PUBLISHER_CONTROLLER_PATH)
public class PublisherController {

    //-------------------------< Services >-------------------------//
    private final PublisherService publisherService;

    private final BookService bookService;

    //-------------------------< Mappers >--------------------------//
    private final PublisherMapper publisherMapper;

    private final BookMapper bookMapper;

    @GetMapping("/{" + PathSettings.PUBLISHER_ID_PATH_VAR_NAME + "}")
    public ResponseEntity<PublisherDto> getPublisherById(@PathVariable(name = PathSettings.PUBLISHER_ID_PATH_VAR_NAME) Long publisherId)
            throws EntityNotFoundException {
        /* Получение издателя из сервиса. */
        Publisher publisher = publisherService.getPublisher(publisherId);
        /* Конвертирование в формат Dto */
        PublisherDto publisherDto = publisherMapper.toDto(publisher);
        return new ResponseEntity<>(publisherDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PublisherDto>> getPublisherList(
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
        List<PublisherDto> publisherDtos = publisherService.getPublishersPage(pageable)
                .stream()
                .map(publisherMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(publisherDtos, HttpStatus.OK);
    }

    @GetMapping("/{" + PathSettings.PUBLISHER_ID_PATH_VAR_NAME + "}/" + PathSettings.BOOK_CONTROLLER_PATH)
    public ResponseEntity<List<BookDto>> getPublisherBooksList(
            @PathVariable(name = PathSettings.PUBLISHER_ID_PATH_VAR_NAME) Long publisherId,
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
        List<BookDto> bookDtos = bookService.getAllBooksByPublisherId(publisherId, pageable)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addPublisher(@RequestBody @Valid PublisherDto publisherDto) {
        /* Конвертирование в формат Entity */
        Publisher publisher = publisherMapper.toEntity(publisherDto);
        /* Добавление издателя с помощью сервиса */
        publisherService.addPublisher(publisher);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{" + PathSettings.PUBLISHER_ID_PATH_VAR_NAME + "}", consumes = "application/json")
    public ResponseEntity updatePublisher(@PathVariable(name = PathSettings.PUBLISHER_ID_PATH_VAR_NAME) Long publisherId,
                                          @RequestBody @Valid PublisherDto publisherDto)
            throws EntityNotFoundException {
        /* Конвертирование в формат Entity */
        Publisher publisher = publisherMapper.toEntity(publisherDto);
        /* Обновление издателя с помощью сервиса */
        publisherService.updatePublisher(publisherId, publisher);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{" + PathSettings.PUBLISHER_ID_PATH_VAR_NAME + "}")
    public ResponseEntity deletePublisher(@PathVariable(name = PathSettings.PUBLISHER_ID_PATH_VAR_NAME) Long publisherId)
            throws EntityNotFoundException {
        /* Удлаление издателя с помощью сервиса */
        publisherService.deletePublisher(publisherId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}