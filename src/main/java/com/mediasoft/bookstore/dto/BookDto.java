package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class BookDto {

    private final Long id;

    @NotNull
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[-0-9 ]{17}$|" +
            "[-0-9X ]{13}$|[0-9X]{10}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?(?:[0-9]+[- ]?){2}[0-9X]",
            message = "Введёный ISBN не соответствует шаблону. Корректный пример: ISBN 978-5-93286-181-3.")
    private final String isbn;

    @NotNull
    private final PublisherDto publisherDto;

    @NotNull
    private final AuthorDto authorDto;

    @NotNull
    @Size(max = 2020, message = "Год написания книги должен быть в пределах" +
            " от рождества Христова до сегодняшних дней.")
    private final Integer year;

    @NotNull
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9 ]{2,40}$",
            message = "Название должно состоять из латиницы/кириллицы и содержать от 2 до 40 символов.")
    private final String title;

    @NotNull
    @Size(message = "Значение не может быть отрицательным или бесконечным.")
    private final Integer price;

    private final Set<Long> warehouseBooksId;
}