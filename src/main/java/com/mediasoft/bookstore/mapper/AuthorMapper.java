package com.mediasoft.bookstore.mapper;

import com.mediasoft.bookstore.dto.AuthorDto;
import com.mediasoft.bookstore.entity.Author;

public interface AuthorMapper {

    Author toEntity(AuthorDto authorDto);

    AuthorDto toDto(Author author);
}
