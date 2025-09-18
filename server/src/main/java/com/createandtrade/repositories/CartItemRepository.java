package com.createandtrade.repositories;

import com.createandtrade.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью CartItem.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
