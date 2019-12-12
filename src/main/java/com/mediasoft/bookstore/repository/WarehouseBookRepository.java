package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.WarehouseBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseBookRepository extends CrudRepository<WarehouseBook, Long> {
}
