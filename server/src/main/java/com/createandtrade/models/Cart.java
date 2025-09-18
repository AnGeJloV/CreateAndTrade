package com.createandtrade.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность Корзина.
 * У каждого пользователя есть одна персональная корзина.
 */
@Entity
@Table(name = "carts")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Связь с пользователем.
     * Связь "один к одному": одна корзина на одного пользователя.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @ToString.Exclude
    private User user;

    /**
     * Список элементов в корзине.
     * Связь "один ко многим": одна корзина может содержать много элементов.
     */
    @OneToMany (mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CartItem> items = new ArrayList<>();

    /**
     * Вспомогательный метод для удобного добавления элемента в корзину.
     * Он также устанавливает обратную связь от элемента к корзине.
     */
    public void addItem (CartItem item){
        items.add(item);
        item.setCart(this);
    }
}
