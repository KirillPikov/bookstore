package com.mediasoft.bookstore.mapper;

import com.mediasoft.bookstore.dto.BookDto;
import com.mediasoft.bookstore.entity.Book;

public interface BookMapper {

    Book toEntity(BookDto bookDto);

    BookDto toDto(Book book);
}
