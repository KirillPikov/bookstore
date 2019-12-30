package com.mediasoft.bookstore.entity;

import com.mediasoft.bookstore.entity.enums.ShoppingBasketStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingBasket {

    @Id
    @SequenceGenerator(name="common_seq", sequenceName="common_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="common_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Customer customer;

    @OneToMany(mappedBy = "shoppingBasket", cascade = CascadeType.REMOVE)
    private List<ShoppingBasketBook> shoppingBasketBooks;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ShoppingBasketStatus shoppingBasketStatus;
}