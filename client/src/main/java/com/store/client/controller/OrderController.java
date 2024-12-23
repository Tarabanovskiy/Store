package com.store.client.controller;

import com.store.client.DTO.OrderDTO;
import com.store.client.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для управления заказами.
 * Обрабатывает запросы на отображение, добавление и удаление заказов.
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Конструктор контроллера для внедрения зависимости OrderService.
     *
     * @param orderService Сервис для работы с заказами.
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Отображает страницу панели управления заказами.
     *
     * @return Название шаблона для страницы панели управления заказами.
     */
    @GetMapping("/order_dashboard")
    public String orderDashboard() {
        return "dashboard/order_dashboard";
    }

    /**
     * Получает список всех заказов и отображает их на странице.
     * Проверяет наличие токена авторизации в сессии.
     *
     * @param model Модель для передачи атрибутов на страницу.
     * @param session Сессия для получения токена авторизации.
     * @return Название шаблона с заказами.
     */
    @GetMapping("/list")
    public String getAllOrders(Model model, HttpSession session) {
        try {
            String token = (String) session.getAttribute("jwtToken");
            if (token == null) {
                model.addAttribute("error", "Необходимо выполнить вход.");
                return "auth/login";
            }
            List<OrderDTO> orderDTOs = orderService.getAllOrders(token);
            model.addAttribute("orders", orderDTOs);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка получения списка заказов: " + e.getMessage());
        }
        return "order/orders_list";
    }

    /**
     * Отображает форму для добавления нового заказа.
     *
     * @param model Модель для передачи атрибутов на страницу.
     * @return Название шаблона для формы добавления заказа.
     */
    @GetMapping("/add")
    public String showAddOrderForm(Model model) {
        return "order/order_add"; // Показывает форму добавления заказа
    }

    /**
     * Обрабатывает добавление нового заказа.
     * Извлекает параметры из запроса и вызывает метод сервиса для добавления заказа.
     * Проверяет наличие токена авторизации в сессии.
     *
     * @param params Параметры запроса, содержащие информацию о заказе.
     * @param model Модель для передачи атрибутов на страницу.
     * @param session Сессия для получения токена авторизации.
     * @return Название шаблона для формы добавления заказа.
     */
    @PostMapping("/add")
    public String addOrder(@RequestParam Map<String, String> params, Model model, HttpSession session) {
        try {
            String token = (String) session.getAttribute("jwtToken");
            if (token == null) {
                model.addAttribute("error", "Необходимо выполнить вход.");
                return "auth/login";
            }

            // Извлекаем параметры из запроса
            Long productId = Long.valueOf(params.get("productId"));
            int quantity = Integer.parseInt(params.get("quantity"));
            LocalDate orderDate = LocalDate.parse(params.get("orderDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));  // Указание формата

            // Вызов метода сервиса для добавления заказа
            orderService.addOrder(productId, quantity, orderDate, token);
            model.addAttribute("success", "Заказ успешно добавлен!");
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка добавления заказа: " + e.getMessage());
        }
        return "order/order_add";
    }

    /**
     * Обрабатывает удаление заказа по ID.
     * Проверяет наличие токена авторизации в сессии.
     *
     * @param id Идентификатор заказа.
     * @param session Сессия для получения токена авторизации.
     * @param model Модель для передачи атрибутов на страницу.
     * @return Переадресует на страницу списка заказов или отображает ошибку.
     */
    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id, HttpSession session, Model model) {
        try {
            String token = (String) session.getAttribute("jwtToken");
            if (token == null) {
                model.addAttribute("error", "Необходимо выполнить вход.");
                return "auth/login";
            }
            orderService.deleteOrder(id, token);
            return "redirect:/orders/list";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка удаления заказа: " + e.getMessage());
            return "order/orders_list";
        }
    }
}
