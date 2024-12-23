package com.server.store.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

/**
 * Сущность, представляющая заказ в системе.
 * Содержит информацию о продукте, количестве, дате заказа и стоимости.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /**
     * Идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор пользователя, создавшего заказ.
     */
    @Column(nullable = false)
    private Long createdBy;

    /**
     * Продукт, связанный с заказом.
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Количество заказанных товаров.
     */
    @Column(nullable = false)
    private int quantity;

    /**
     * Дата заказа.
     * Использует формат "dd-MM-yyyy" для отображения.
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")  // Формат для LocalDate
    private LocalDate orderDate;

    /**
     * Общая стоимость заказа, вычисляется автоматически.
     */
    @Column(nullable = false)
    private BigDecimal totalCost;

    /**
     * Метод, который выполняется перед сохранением заказа в базе данных.
     * Рассчитывает общую стоимость заказа на основе цены продукта и количества.
     */
    @PrePersist
    public void calculateTotalCost() {
        if (product != null && product.getPrice() != null) {
            totalCost = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        }
    }
}
