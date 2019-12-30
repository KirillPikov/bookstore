package com.mediasoft.bookstore.mapper;

import com.mediasoft.bookstore.dto.PublisherDto;
import com.mediasoft.bookstore.entity.Publisher;

public interface PublisherMapper {

    Publisher toEntity(PublisherDto publisherDto);

    PublisherDto toDto(Publisher publisher);
}
