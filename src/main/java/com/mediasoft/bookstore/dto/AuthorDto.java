package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class AuthorDto {

    private final Long id;

    @NotNull
    @Pattern(regexp = "^[а-яА-Я ]{2,30}$",
            message = "Имя должно состоять из русских букв и содеражать от 2 до 30 символов.")
    private final String name;

    @Pattern(regexp = "^[а-яА-Я ]{4,25}[0-9]{0,7}$",
            message = "Адрес должен состоять из русских букв и содеражать от 4 до 32 символов.")
    private final String address;

    @NotNull
    @Email(regexp = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$",
            message = "Введёная строка не является e-mail.")
    private final String email;

    private final Set<Long> booksId;
}