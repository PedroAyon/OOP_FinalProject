package com.pedroayonb.product_api.domain.repository;

import com.pedroayonb.product_api.domain.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p where p.name LIKE :name")
    Iterable<Product> findByName(@Param("name") String name);
}
