package com.server.store.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) для представления данных продукта.
 * Используется для передачи информации о продукте между слоями приложения.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    /**
     * Идентификатор продукта.
     */
    private Long id;

    /**
     * Название продукта.
     */
    private String name;

    /**
     * Цена продукта.
     */
    private BigDecimal price;

    /**
     * Количество продукта в наличии.
     */
    private Integer quantity;

    /**
     * Категория продукта.
     */
    private String category;

    /**
     * Идентификатор пользователя, который создал продукт.
     */
    private Long createdBy;
}
