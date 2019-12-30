package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mediasoft.bookstore.dto.converter.AuthorIdToAuthorDtoConverter;
import com.mediasoft.bookstore.dto.converter.PublisherIdToPublisherDtoConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public final class BookDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotNull(message = "Поле isbn должно быть задано.")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[-0-9 ]{17}$|" +
            "[-0-9X ]{13}$|[0-9X]{10}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?(?:[0-9]+[- ]?){2}[0-9X]",
            message = "Введёный ISBN не соответствует шаблону. Корректный пример: ISBN 978-5-93286-181-3.")
    private final String isbn;

    @NotNull(message = "Поле publisher должно быть задано.")
    @JsonProperty(value = "publisher")
    @JsonDeserialize(converter = PublisherIdToPublisherDtoConverter.class)
    private final PublisherDto publisherDto;

    @NotNull(message = "Поле author должно быть задано.")
    @JsonProperty(value = "author")
    @JsonDeserialize(converter = AuthorIdToAuthorDtoConverter.class)
    private final AuthorDto authorDto;

    @NotNull(message = "Поле year должно быть задано.")
    @Range(max = 2020, message = "Год написания книги должен находиться в пределах" +
                                 " от рождества Христова до сегодняшних дней.")
    private final Integer year;

    @NotNull(message = "Поле title должно быть задано.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9 ]{2,40}$",
            message = "Название должно состоять из латиницы/кириллицы и содержать от 2 до 40 символов.")
    private final String title;

    @NotNull(message = "Поле price должно быть задано.")
    @Range(max = 1000000000 /* 1 миллиард */, message = "Значение не может быть отрицательным или бесконечным.")
    private final Integer price;
}