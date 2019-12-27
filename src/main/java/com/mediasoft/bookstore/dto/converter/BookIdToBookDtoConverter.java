package com.mediasoft.bookstore.dto.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.mediasoft.bookstore.dto.BookDto;
import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.BookMapper;
import com.mediasoft.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class BookIdToBookDtoConverter extends StdConverter<Long, BookDto> {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public BookDto convert(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Book with id = " + bookId + " not found.");
                        }
        );
        return bookMapper.toDto(book);
    }
}