package com.mediasoft.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ShoppingBasketBook {

    @Id
    @SequenceGenerator(name="common_seq", sequenceName="common_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="common_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ShoppingBasket shoppingBasket;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn
    private Book book;

    private Integer count;
}