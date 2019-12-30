package com.mediasoft.bookstore.config;

/**
 * Класс с настройками пагинации.
 */
public final class PaginationSettings {

    /**
     * Поле по умолчанию для сотрировки.
     */
    public final static String DEFAULT_SORTING_FIELD = "id";

    /**
     * Направление сортировки по умолчанию.
     */
    public final static String DEFAULT_SORTING = "DESC";

    /**
     * Количество элементов при пагинации по умолчанию.
     */
    public final static String DEFAULT_ELEMENTS_COUNT = "5";

    /**
     * Первая страница при пагинации по умолчанию.
     */
    public final static String DEFAULT_PAGE = "0";

    private PaginationSettings() {
    }
}
