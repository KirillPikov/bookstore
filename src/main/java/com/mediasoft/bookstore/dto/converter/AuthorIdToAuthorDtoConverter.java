package com.mediasoft.bookstore.dto.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.mediasoft.bookstore.dto.AuthorDto;
import com.mediasoft.bookstore.entity.Author;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.AuthorMapper;
import com.mediasoft.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AuthorIdToAuthorDtoConverter extends StdConverter<Long, AuthorDto> {

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto convert(Long authorId) throws EntityNotFoundException {
        Author author = authorService.getAuthor(authorId);
        return authorMapper.toDto(author);
    }
}
