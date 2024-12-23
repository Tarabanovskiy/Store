package com.server.store.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * Класс, представляющий запрос на создание заказа.
 * Содержит информацию о продукте, количестве и дате заказа.
 */
@Data
public class OrderRequest {

    /**
     * Идентификатор продукта, который заказывается.
     *
     * @return Идентификатор продукта.
     */
    private Long productId;

    /**
     * Количество заказанного продукта.
     *
     * @return Количество продукта.
     */
    private int quantity;

    /**
     * Дата размещения заказа.
     * Формат: yyyy-MM-dd
     *
     * @return Дата заказа.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
}
