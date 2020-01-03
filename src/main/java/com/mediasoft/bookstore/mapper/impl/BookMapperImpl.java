package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.BookDto;
import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.mapper.AuthorMapper;
import com.mediasoft.bookstore.mapper.BookMapper;
import com.mediasoft.bookstore.mapper.PublisherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class BookMapperImpl implements BookMapper {

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
                    /* Конвертирование Dto в Entity */
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
                    book.getPrice()
            );
        }
        return bookDto;
    }
}