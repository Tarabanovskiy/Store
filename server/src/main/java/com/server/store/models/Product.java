package com.server.store.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Сущность, представляющая продукт в системе.
 * Содержит информацию о названии, цене, количестве, категории и пользователе, который создал продукт.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Уникальный идентификатор продукта.
     * Генерируется автоматически при создании нового продукта.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название продукта.
     * Не может быть null.
     *
     * @return название продукта
     */
    @Column(nullable = false)
    private String name;

    /**
     * Цена продукта.
     * Не может быть null.
     *
     * @return цена продукта
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Количество продукта.
     * Не может быть null.
     *
     * @return количество продукта
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * Категория продукта.
     * Не может быть null.
     *
     * @return категория продукта
     */
    @Column(nullable = false)
    private String category;

    /**
     * Идентификатор пользователя, который создал продукт.
     * Не может быть null.
     *
     * @return идентификатор пользователя
     */
    @Column(nullable = false)
    private Long createdBy;
}
