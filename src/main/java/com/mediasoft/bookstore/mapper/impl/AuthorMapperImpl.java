package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.AuthorDto;
import com.mediasoft.bookstore.entity.Author;
import com.mediasoft.bookstore.entity.Book;
import com.mediasoft.bookstore.mapper.AuthorMapper;
import com.mediasoft.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AuthorMapperImpl implements AuthorMapper {

    private final BookRepository bookRepository;

    @Override
    public Author toEntity(AuthorDto authorDto) {
        Author author;
        if (authorDto == null) {
            author = null;
        } else {
            author = new Author();
            author.setId(authorDto.getId());
            author.setName(authorDto.getName());
            author.setAddress(authorDto.getAddress());
            author.setEmail(authorDto.getEmail());
            author.setBooks(
                    (LinkedList<Book>) bookRepository.findAllById(
                            authorDto.getBooksId()
                    )
            );
        }
        return author;
    }

    @Override
    public AuthorDto toDto(Author author) {
        AuthorDto authorDto;
        if (author == null) {
            authorDto = null;
        } else {
            authorDto = new AuthorDto(
                    author.getId(),
                    author.getName(),
                    author.getAddress(),
                    author.getEmail(),
                    author.getBooks().stream()
                            .map(Book::getId)
                            .collect(Collectors.toSet())
            );
        }
        return authorDto;
    }
}
