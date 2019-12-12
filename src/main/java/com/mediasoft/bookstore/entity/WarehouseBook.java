package com.mediasoft.bookstore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WarehouseBook {

    @Id
    @SequenceGenerator(name="common_seq", sequenceName="common_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="common_seq")
    private Long id;

    @ManyToOne
    @JoinColumn
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn
    private Book book;

    private Integer count;
}
