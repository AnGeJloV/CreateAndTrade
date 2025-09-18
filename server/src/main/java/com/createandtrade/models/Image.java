package com.createandtrade.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Сущность Изображение.
 * Хранит как метаданные файла, так и сам файл в виде массива байт.
 */
@Entity
@Table(name = "images")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Уникальное имя файла на сервере (например, сгенерированное UUID). */
    @Column(name = "name")
    private String name;

    /** Оригинальное имя файла, которое было при загрузке. */
    @Column(name = "original_file_name")
    private String originalFileName;

    /** Размер файла в байтах. */
    @Column(name = "size")
    private Long size;

    /** MIME-тип файла (например, "image/png", "image/jpeg"). */
    @Column(name = "content_type")
    private String contentType;

    /** Содержимое файла, хранящееся в виде массива байт. */
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @ToString.Exclude
    private byte[] bytes;

    /**
     * Связь с товаром, которому принадлежит изображение.
     * Много изображений могут принадлежать одному товару.
     * В этой таблице будет внешний ключ 'product_id'.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;
}
