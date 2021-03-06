package com.mediasoft.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @SequenceGenerator(name="common_seq", sequenceName="common_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="common_seq")
    private Long id;

    private String email;

    private String name;

    private String phone;

    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<ShoppingBasket> shoppingBaskets;
}