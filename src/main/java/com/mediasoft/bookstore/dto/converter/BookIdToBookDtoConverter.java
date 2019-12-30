package com.mediasoft.bookstore.dto.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.mediasoft.bookstore.dto.BookDto;
import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.mapper.BookMapper;
import com.mediasoft.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class BookIdToBookDtoConverter extends StdConverter<Long, BookDto> {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @Override
    public BookDto convert(Long bookId) {
        Book book = bookService.getBookById(bookId);
        return bookMapper.toDto(book);
    }
}