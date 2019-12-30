package com.mediasoft.bookstore.dto.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.mediasoft.bookstore.dto.PublisherDto;
import com.mediasoft.bookstore.entity.Publisher;
import com.mediasoft.bookstore.mapper.PublisherMapper;
import com.mediasoft.bookstore.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class PublisherIdToPublisherDtoConverter extends StdConverter<Long, PublisherDto> {

    private final PublisherService publisherService;

    private final PublisherMapper publisherMapper;

    @Override
    public PublisherDto convert(Long publisherId) {
        Publisher publisher = publisherService.getPublisher(publisherId);
        return publisherMapper.toDto(publisher);
    }
}