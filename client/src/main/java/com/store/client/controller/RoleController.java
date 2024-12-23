package com.store.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов на страницы панели управления.
 * Обрабатывает запросы для различных разделов панели управления: основной, продуктов и заказов.
 */
@Controller
public class RoleController {

    /**
     * Обрабатывает запрос на главную страницу панели управления.
     * Отправляет пользователя на шаблон с основной панелью управления.
     *
     * @return Название шаблона для главной страницы панели управления.
     */
    @GetMapping("/dashboard/main_dashboard")
    public String mainDashboard() {
        return "dashboard/main_dashboard";  // Название вашего шаблона
    }

    /**
     * Обрабатывает запрос на страницу панели управления продуктами.
     * Отправляет пользователя на шаблон с панелью управления продуктами.
     *
     * @return Название шаблона для страницы панели управления продуктами.
     */
    @GetMapping("/dashboard/product_dashboard")
    public String productDashboard() {
        return "dashboard/product_dashboard";  // Название вашего шаблона
    }

    /**
     * Обрабатывает запрос на страницу панели управления заказами.
     * Отправляет пользователя на шаблон с панелью управления заказами.
     *
     * @return Название шаблона для страницы панели управления заказами.
     */
    @GetMapping("/dashboard/order_dashboard")
    public String orderDashboard() {
        return "dashboard/order_dashboard";  // Название вашего шаблона
    }
}
