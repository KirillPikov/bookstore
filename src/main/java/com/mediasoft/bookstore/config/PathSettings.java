package com.mediasoft.bookstore.config;

/**
 * Класс с настройками путей для контроллеров.
 */
public final class PathSettings {
    //-----------------------------< Request Params >--------------------------------------------//
    /**
     * Название параметра, отвечающего за номер страницы при пагинации.
     */
    public final static String PAGE_REQUEST_PARAM = "page";

    /**
     * Название параметра, отвечающего за количество элементов на странице при пагинации.
     */
    public final static String COUNT_REQUEST_PARAM = "count";

    /**
     * Название параметра, отвечающего поле, по которому будут сортировться элементы при пагинации.
     */
    public final static String SORTING_FIELD_REQUEST_PARAM = "sortingField";

    /**
     * Название параметра, отвечающего за направление сортировки при пагинации.
     */
    public final static String SORTING_REQUEST_PARAM = "sort";

    //-----------------------------< Path Variables >--------------------------------------------//
    /**
     * Название переменной ID покупателя в адресной строке.
     */
    public final static String CUSTOMER_ID_PATH_VAR_NAME = "customerId";
    /**
     * Название переменной ID автора в адресной строке.
     */
    public final static String AUTHOR_ID_PATH_VAR_NAME = "authorId";
    /**
     * Название переменной ID издателя в адресной строке.
     */
    public final static String PUBLISHER_ID_PATH_VAR_NAME = "publisherId";
    /**
     * Название переменной ID склада в адресной строке.
     */
    public final static String WAREHOUSE_ID_PATH_VAR_NAME = "publisherId";
    /**
     * Название переменной страницы корзины в адресной строке.
     */
    public final static String SHOPPING_BASKET_NUM_PATH_VAR_NAME = "shoppingBasketNum";
    /**
     * Название переменной страницы книги в корзине в адресной строке.
     */
    public final static String SHOPPING_BASKET_BOOK_NUM_PATH_VAR_NAME = "shoppingBasketBookNum";

    //-----------------------------< Controller paths >------------------------------------------//
    /**
     * Адрес для контроллера Customer. {@link com.mediasoft.bookstore.controller.CustomerController}
     */
    public final static String CUSTOMER_CONTROLLER_PATH = "customers";
    /**
     * Адрес для контроллера ShoppingBasket. {@link com.mediasoft.bookstore.controller.ShoppingBasketController}
     */
    public final static String SHOPPING_BASKET_CONTROLLER_PATH = CUSTOMER_CONTROLLER_PATH + "/{" + CUSTOMER_ID_PATH_VAR_NAME + "}/" + "shoppingBaskets";
    /**
     * Адрес для контроллера ShoppingBasketBook. {@link com.mediasoft.bookstore.controller.ShoppingBasketBookController}
     */
    public final static String SHOPPING_BASKET_BOOK_CONTROLLER_PATH = SHOPPING_BASKET_CONTROLLER_PATH + "/{" + SHOPPING_BASKET_NUM_PATH_VAR_NAME + "}/" + "shoppingBasketBooks";
    /**
     * Адрес для контроллера Customer. {@link com.mediasoft.bookstore.controller.CustomerController}
     */
    public final static String AUTHOR_CONTROLLER_PATH = "authors";
    /**
     * Адрес для контроллера Publisher. {@link com.mediasoft.bookstore.controller.PublisherController}
     */
    public final static String PUBLISHER_CONTROLLER_PATH = "publishers";
    /**
     * Адрес для контроллера Warehouse. {@link com.mediasoft.bookstore.controller.WarehouseController}
     */
    public final static String WAREHOUSE_CONTROLLER_PATH = "warehouses";
    /**
     * Адрес для контроллера Book. {@link com.mediasoft.bookstore.controller} //TODO BookController
     */
    public final static String BOOK_CONTROLLER_PATH = "books";

    private PathSettings() {
    }
}