package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.PublisherDto;
import com.mediasoft.bookstore.entity.Publisher;
import com.mediasoft.bookstore.mapper.PublisherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class PublisherMapperImpl implements PublisherMapper {

    @Override
    public Publisher toEntity(PublisherDto publisherDto) {
        Publisher publisher;
        if (publisherDto == null) {
            publisher = null;
        } else {
            publisher = new Publisher();
            publisher.setId(publisherDto.getId());
            publisher.setName(publisherDto.getName());
            publisher.setAddress(publisherDto.getAddress());
            publisher.setPhone(publisherDto.getPhone());
            publisher.setEmail(publisherDto.getEmail());
        }
        return publisher;
    }

    @Override
    public PublisherDto toDto(Publisher publisher) {
        PublisherDto publisherDto;
        if (publisher == null) {
            publisherDto = null;
        } else {
            publisherDto = new PublisherDto(
                    publisher.getId(),
                    publisher.getName(),
                    publisher.getAddress(),
                    publisher.getPhone(),
                    publisher.getEmail()
            );
        }
        return publisherDto;
    }
}