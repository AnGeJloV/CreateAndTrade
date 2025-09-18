package com.createandtrade.models;

import com.createandtrade.models.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность Товар.
 * Основная сущность маркетплейса, описывающая товар, выставленный на продажу.
 */
@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    /** DECIMAL используется для точности финансовых расчетов. */
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private double price;

    @Column(nullable = false)
    private String city;

    @CreationTimestamp
    @Column(name = "date_of_created", updatable = false)
    private LocalDateTime dateOfCreated;

    /**
     * Пользователь (продавец), который создал товар.
     * Связь "многие к одному": много товаров могут принадлежать одному пользователю.
     * В этой таблице будет внешний ключ 'user_id'.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    /**
     * Текущий статус товара.
     * По умолчанию PENDING, т.к. все новые товары должны проходить модерацию.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status = ProductStatus.PENDING;

    /**
     * Категория, к которой относится товар.
     * Связь "многие к одному": много товаров могут принадлежать одной категории.
     * В этой таблице будет внешний ключ 'category_id'.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    /**
     * Список изображений товара.
     * Связь "один ко многим": один товар может иметь много изображений.
     * mappedBy = "product" указывает, что эта сторона не владеет связью (внешний ключ в таблице 'images').
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product", orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Image> images = new ArrayList<>();
}
