package com.mediasoft.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingBasket {

    @Id
    @SequenceGenerator(name="common_seq", sequenceName="common_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="common_seq")
    private Long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @OneToMany
    private List<ShoppingBasketBook> shoppingBasketBook;
}
