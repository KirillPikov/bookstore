package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public final class AuthorDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotNull(message = "Поле name должно быть задано.")
    @Pattern(regexp = "^[а-яА-Я ]{2,30}$",
            message = "Имя должно состоять из русских букв и содеражать от 2 до 30 символов.")
    private final String name;

    @Pattern(regexp = "^[а-яА-Я ]{4,25}[0-9]{0,7}$",
            message = "Адрес должен состоять из русских букв и содеражать от 4 до 32 символов.")
    private final String address;

    @NotNull(message = "Поле email должно быть задано.")
    @Email(regexp = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$",
            message = "Введёная строка не является e-mail.")
    private final String email;
}