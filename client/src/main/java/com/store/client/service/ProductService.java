package com.store.client.service;

import com.store.client.DTO.ProductDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String backendUrl = "http://localhost:8080/api/products";

    /**
     * Создает новый продукт.
     *
     * @param name     Название продукта.
     * @param price    Цена продукта.
     * @param quantity Количество продукта.
     * @param category Категория продукта.
     * @param token    Токен авторизации.
     * @return Ответ от сервера с результатом создания продукта.
     */
    public ResponseEntity<ProductDTO> createProduct(String name, BigDecimal price, int quantity, String category, String token) {
        String url = backendUrl + "/addProduct";
        ProductDTO product = new ProductDTO(null, name, price, quantity, category, null);

        HttpHeaders headers = createHeaders(token);

        HttpEntity<ProductDTO> request = new HttpEntity<>(product, headers);

        return restTemplate.exchange(url, HttpMethod.POST, request, ProductDTO.class);
    }

    /**
     * Редактирует существующий продукт.
     *
     * @param id          Идентификатор продукта.
     * @param name        Название продукта.
     * @param price       Цена продукта.
     * @param quantity    Количество продукта.
     * @param category    Категория продукта.
     * @param token       Токен авторизации.
     * @return Ответ от сервера с результатом редактирования продукта.
     */
    public ResponseEntity<ProductDTO> editProduct(Long id, String name, BigDecimal price, int quantity, String category, String token) {
        String url = backendUrl + "/api/products/" + id;  // Путь до продукта

        // Создаем объект ProductDTO с обновленными данными
        ProductDTO product = new ProductDTO(id, name, price, quantity, category, null);

        // Устанавливаем токен в заголовках
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);  // Передаем токен как часть заголовка

        // Отправляем PUT запрос на сервер с данными продукта
        HttpEntity<ProductDTO> request = new HttpEntity<>(product, headers);

        return restTemplate.exchange(url, HttpMethod.PUT, request, ProductDTO.class);  // Отправка PUT запроса
    }

    /**
     * Получает все продукты.
     *
     * @param token Токен авторизации.
     * @return Список всех продуктов.
     */
    public ResponseEntity<List<ProductDTO>> getAllProducts(String token) {
        String url = backendUrl + "/allProducts";

        HttpHeaders headers = createHeaders(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<ProductDTO>>() {});
    }

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id    Идентификатор продукта.
     * @param token Токен авторизации.
     * @return Ответ с результатом удаления продукта.
     */
    public ResponseEntity<Void> deleteProduct(Long id, String token) {
        String url = backendUrl + "/" + id;

        HttpHeaders headers = createHeaders(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    /**
     * Создает заголовки для запроса с токеном.
     * Заголовки включают токен авторизации и тип контента.
     *
     * @param token Токен авторизации.
     * @return Заголовки для HTTP запроса.
     */
    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
