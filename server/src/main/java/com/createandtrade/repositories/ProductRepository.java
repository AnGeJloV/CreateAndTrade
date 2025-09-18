package com.createandtrade.repositories;

import com.createandtrade.models.Product;
import com.createandtrade.models.User;
import com.createandtrade.models.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /** Находит все товары, принадлежащие конкретному пользователю. */
    List<Product> findByUser (User user);

    /** Находит все товары конкретного пользователя с определенным статусом */
    List<Product> findByUserAndStatus (User user, ProductStatus status);
}
