package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.BookDto;
import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.entity.WarehouseBook;
import com.mediasoft.bookstore.mapper.AuthorMapper;
import com.mediasoft.bookstore.mapper.BookMapper;
import com.mediasoft.bookstore.mapper.PublisherMapper;
import com.mediasoft.bookstore.repository.WarehouseBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class BookMapperImpl implements BookMapper {

    private final WarehouseBookRepository warehouseBookRepository;

    private final PublisherMapper publisherMapper;

    private final AuthorMapper authorMapper;
    
    @Override
    public Book toEntity(BookDto bookDto) {
        Book book;
        if (bookDto == null) {
            book = null;
        } else {
            book = new Book();
            book.setId(bookDto.getId());
            book.setIsbn(bookDto.getIsbn());
            book.setPublisher(
                    /* Конвертирование Dto в Entity */
                    publisherMapper.toEntity(
                            bookDto.getPublisherDto()
                    )
            );
            book.setAuthor(
                    authorMapper.toEntity(
                            bookDto.getAuthorDto()
                    )
            );
            book.setYear(bookDto.getYear());
            book.setTitle(bookDto.getTitle());
            book.setPrice(bookDto.getPrice());
        }
        return book;
    }

    @Override
    public BookDto toDto(Book book) {
        BookDto bookDto;
        if (book == null) {
            bookDto = null;
        } else {
            bookDto = new BookDto(
                    book.getId(),
                    book.getIsbn(),
                    publisherMapper.toDto(book.getPublisher()),
                    authorMapper.toDto(book.getAuthor()),
                    book.getYear(),
                    book.getTitle(),
                    book.getPrice(),
                    book.getWarehouseBook().stream()
                            .map(WarehouseBook::getId)
                            .collect(Collectors.toSet())
            );
        }
        return bookDto;
    }
}
