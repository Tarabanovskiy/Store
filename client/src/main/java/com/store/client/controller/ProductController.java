package com.store.client.controller;

import com.store.client.DTO.ProductDTO;
import com.store.client.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Контроллер для управления продуктами.
 * Обрабатывает запросы на отображение, добавление и удаление продуктов.
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Конструктор контроллера для внедрения зависимости ProductService.
     *
     * @param productService Сервис для работы с продуктами.
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Отображает страницу панели управления продуктами.
     *
     * @return Название шаблона для страницы панели управления продуктами.
     */
    @GetMapping("/product_dashboard")
    public String productDashboard() {
        return "dashboard/product_dashboard";  // Название вашего шаблона
    }

    /**
     * Получает список всех продуктов и отображает их на странице.
     * Проверяет наличие токена авторизации в сессии.
     *
     * @param model Модель для передачи атрибутов на страницу.
     * @param session Сессия для получения токена авторизации.
     * @return Название шаблона с продуктами.
     */
    @GetMapping("/list")
    public String getAllProducts(Model model, HttpSession session) {
        try {
            String token = (String) session.getAttribute("jwtToken");
            if (token == null) {
                model.addAttribute("error", "Необходимо выполнить вход.");
                return "auth/login";
            }
            ResponseEntity<List<ProductDTO>> response = productService.getAllProducts(token);
            List<ProductDTO> products = response.getBody();
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка получения списка продуктов: " + e.getMessage());
        }
        return "product/products_list";
    }

    /**
     * Отображает форму для добавления нового продукта.
     *
     * @param model Модель для передачи атрибутов на страницу.
     * @return Название шаблона для формы добавления продукта.
     */
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        return "product/product_add";  // Показывает форму добавления продукта
    }

    /**
     * Обрабатывает добавление нового продукта.
     * Проверяет наличие токена авторизации в сессии.
     *
     * @param name Название продукта.
     * @param price Цена продукта.
     * @param quantity Количество продукта.
     * @param category Категория продукта.
     * @param model Модель для передачи атрибутов на страницу.
     * @param session Сессия для получения токена авторизации.
     * @return Название шаблона для формы добавления продукта.
     */
    @PostMapping("/add")
    public String addProduct(@RequestParam String name,
                             @RequestParam BigDecimal price,
                             @RequestParam int quantity,
                             @RequestParam String category,
                             Model model,
                             HttpSession session) {
        try {
            String token = (String) session.getAttribute("jwtToken");
            if (token == null) {
                model.addAttribute("error", "Необходимо выполнить вход.");
                return "auth/login";
            }
            productService.createProduct(name, price, quantity, category, token);
            model.addAttribute("success", "Продукт успешно добавлен!");
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка добавления продукта: " + e.getMessage());
        }
        return "product/product_add";
    }

    /**
     * Обрабатывает удаление продукта по ID.
     * Проверяет наличие токена авторизации в сессии.
     *
     * @param id Идентификатор продукта.
     * @param session Сессия для получения токена авторизации.
     * @param model Модель для передачи атрибутов на страницу.
     * @return Переадресует на страницу списка продуктов или отображает ошибку.
     */
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session, Model model) {
        try {
            String token = (String) session.getAttribute("jwtToken");
            if (token == null) {
                model.addAttribute("error", "Необходимо выполнить вход.");
                return "auth/login";
            }
            productService.deleteProduct(id, token);
            return "redirect:/products/list";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка удаления продукта: " + e.getMessage());
            return "product/products_list";
        }
    }
}
