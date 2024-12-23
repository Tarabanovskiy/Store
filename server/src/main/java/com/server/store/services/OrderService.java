package com.server.store.services;

import com.server.store.exceptions.ResourceNotFoundException;
import com.server.store.models.*;
import com.server.store.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для управления заказами.
 * Предоставляет функциональность для получения, создания и удаления заказов.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Получает список всех заказов.
     *
     * @return список всех заказов
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Получает статистику заказов, сгруппированных по дате.
     * Статистика представлена в виде карты, где ключом является строковое представление даты,
     * а значением - количество заказов в этот день.
     *
     * @return статистика заказов по датам
     */
    public Map<String, Integer> getOrderStatistics() {
        List<Order> orders = orderRepository.findAll();
        Map<String, Integer> statistics = new HashMap<>();

        for (Order order : orders) {
            String dateString = order.getOrderDate().toString();  // Просто используйте orderDate, так как это уже LocalDate
            statistics.put(dateString, statistics.getOrDefault(dateString, 0) + 1);
        }

        return statistics;
    }

    /**
     * Создает новый заказ.
     * Проверяет корректность данных заказа (наличие продукта и его цены),
     * затем вычисляет общую стоимость заказа и сохраняет его в базе данных.
     *
     * @param order объект заказа, который нужно создать
     * @return созданный заказ
     * @throws IllegalArgumentException если цена продукта или сам продукт не указаны
     */
    public Order createOrder(Order order) {
        // Проверяем, есть ли продукт и его цена
        if (order.getProduct() == null || order.getProduct().getPrice() == null) {
            throw new IllegalArgumentException("Цена продукта не может быть null");
        }

        // Вычисляем общую стоимость
        order.calculateTotalCost();

        // Сохраняем заказ
        return orderRepository.save(order);
    }

    /**
     * Удаляет заказ по идентификатору.
     * Если заказ не найден, генерирует исключение {@link ResourceNotFoundException}.
     *
     * @param id идентификатор заказа, который нужно удалить
     * @throws ResourceNotFoundException если заказ с указанным идентификатором не найден
     */
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));
        orderRepository.delete(order);
    }
}
