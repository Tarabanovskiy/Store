package com.store.client.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO для представления заказа.
 * Содержит информацию о заказе, такую как идентификатор, имя продукта, количество, дата заказа и общая стоимость.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    /**
     * Идентификатор заказа.
     * Уникальный идентификатор для каждого заказа.
     */
    private Long id;

    /**
     * Название продукта в заказе.
     * Хранит строковое значение, представляющее имя продукта, который был заказан.
     */
    private String productName;

    /**
     * Количество заказанного продукта.
     * Указывает, сколько единиц продукта было заказано.
     */
    private int quantity;

    /**
     * Дата заказа.
     * Хранит дату, когда заказ был оформлен.
     */
    private LocalDate orderDate;

    /**
     * Общая стоимость заказа.
     * Хранит общую стоимость заказа, вычисленную как цена единицы продукта, умноженная на количество.
     * Представляется в виде {@link BigDecimal} для точных финансовых вычислений.
     */
    private BigDecimal totalCost;

}
