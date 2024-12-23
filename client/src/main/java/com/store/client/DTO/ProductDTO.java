package com.store.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO для представления продукта.
 * Содержит информацию о продукте, такую как его идентификатор, имя, цену, количество, категорию и пользователя, создавшего продукт.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    /**
     * Идентификатор продукта.
     * Уникальный идентификатор для каждого продукта.
     */
    private Long id;

    /**
     * Название продукта.
     * Хранит строковое значение, представляющее название продукта.
     */
    private String name;

    /**
     * Цена продукта.
     * Хранит цену продукта в виде {@link BigDecimal} для точности вычислений с денежными значениями.
     */
    private BigDecimal price;

    /**
     * Количество продукта.
     * Количество данного продукта на складе.
     */
    private Integer quantity;

    /**
     * Категория продукта.
     * Указывает категорию, к которой принадлежит продукт (например, электроника, одежда и т.д.).
     */
    private String category;

    /**
     * Идентификатор пользователя, создавшего продукт.
     * Ссылается на пользователя, который добавил продукт в систему.
     */
    private Long createdBy;
}
