package com.mediasoft.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Publisher {

    @Id
    @SequenceGenerator(name="common_seq", sequenceName="common_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="common_seq")
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    private List<Book> books;
}