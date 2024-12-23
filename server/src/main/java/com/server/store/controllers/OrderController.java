package com.server.store.controllers;

import com.server.store.config.UserDetailsImpl;
import com.server.store.exceptions.ResourceNotFoundException;

import com.server.store.models.Order;
import com.server.store.models.Product;
import com.server.store.repositories.ProductRepository;
import com.server.store.services.OrderService;
import org.hibernate.mapping.List;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для управления заказами.
 * Обрабатывает запросы на создание, удаление и просмотр заказов.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Получает список всех заказов.
     * Доступно только для пользователей с ролью ADMIN или MANAGER.
     *
     * @return Список всех заказов с статусом OK
     */
    @GetMapping("/allOrders")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Получает статистику по заказам.
     * Доступно только для пользователей с ролью ADMIN или MANAGER.
     *
     * @return Статистика по заказам в виде карты, где ключ - название показателя, значение - его количество
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<Map<String, Integer>> getOrderStatistics() {
        Map<String, Integer> statistics = orderService.getOrderStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Добавляет новый заказ.
     * Доступно для пользователей с ролью ADMIN, MANAGER или CUSTOMER.
     *
     * @param orderRequest Объект с данными для создания заказа.
     * @return Созданный заказ с статусом CREATED.
     */
    @PostMapping("/addOrder")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public ResponseEntity<Order> addOrder(@RequestBody OrderRequest orderRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Создаем объект заказа
        Order order = new Order();
        order.setCreatedBy(userDetails.getUser().getId());
        order.setQuantity(orderRequest.getQuantity());
        order.setOrderDate(orderRequest.getOrderDate());  // Используем LocalDate

        // Получаем продукт по его идентификатору
        Product product = productRepository.findById(orderRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден"));
        order.setProduct(product);

        // Рассчитываем общую стоимость заказа
        order.calculateTotalCost();

        // Сохраняем заказ в базе данных
        Order savedOrder = orderService.createOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    /**
     * Удаляет заказ по идентификатору.
     * Доступно только для пользователей с ролью ADMIN или MANAGER.
     *
     * @param id Идентификатор заказа для удаления.
     * @return Ответ с пустым телом и статусом NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
