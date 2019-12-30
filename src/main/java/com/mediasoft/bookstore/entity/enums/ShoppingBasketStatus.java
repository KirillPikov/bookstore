package com.mediasoft.bookstore.entity.enums;

public enum ShoppingBasketStatus {
    /**
     * Корзина находится в стадии формировании.
     */
    ACTIVE,
    /**
     * Формирование корзины было отеменено.
     */
    CANCELED,
    /**
     * Формирование корзины окончено, корзина "заказана".
     */
    ORDERED
}