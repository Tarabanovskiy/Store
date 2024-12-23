package com.store.client.service;

import com.store.client.DTO.OrderDTO;
import com.store.client.DTO.ProductDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для управления заказами.
 */
@Service
public class OrderService {

    private final String backendUrl = "http://localhost:8080/api/orders";
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Получает список всех заказов.
     *
     * @param token Токен авторизации.
     * @return Список всех заказов в виде списка {@link OrderDTO}.
     */
    public List<OrderDTO> getAllOrders(String token) {
        String url = backendUrl + "/allOrders";

        HttpHeaders headers = createHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {});

        // Преобразование данных в объекты OrderDTO
        return response.getBody().stream()
                .map(order -> {
                    Map<String, Object> orderData = (Map<String, Object>) order;

                    // Преобразуем поля в нужные типы данных
                    Long id = ((Number) orderData.get("id")).longValue();
                    String productName = (String) orderData.get("productName");
                    Integer quantity = (Integer) orderData.get("quantity");

                    // Преобразуем строку с датой в LocalDate
                    String orderDateStr = (String) orderData.get("orderDate");
                    LocalDate orderDate = LocalDate.parse(orderDateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                    // Преобразуем стоимость в BigDecimal
                    Double totalCostDouble = (Double) orderData.get("totalCost");
                    BigDecimal totalCost = totalCostDouble != null ? BigDecimal.valueOf(totalCostDouble) : BigDecimal.ZERO;

                    // Возвращаем объект OrderDTO
                    return new OrderDTO(id, productName, quantity, orderDate, totalCost);
                })
                .collect(Collectors.toList());
    }

    /**
     * Добавляет новый заказ.
     *
     * @param productId Идентификатор продукта.
     * @param quantity  Количество заказанного продукта.
     * @param orderDate Дата заказа.
     * @param token     Токен авторизации.
     */
    public void addOrder(Long productId, int quantity, LocalDate orderDate, String token) {
        String url = backendUrl + "/addOrder";

        HttpHeaders headers = createHeaders(token);

        // Формируем JSON-запрос
        String requestJson = """
        {
            "productId": %d,
            "quantity": %d,
            "orderDate": "%s"
        }
    """.formatted(productId, quantity, orderDate.toString());

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }

    /**
     * Удаляет заказ по идентификатору.
     *
     * @param id    Идентификатор заказа.
     * @param token Токен авторизации.
     */
    public void deleteOrder(Long id, String token) {
        String url = backendUrl + "/" + id;

        HttpHeaders headers = createHeaders(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    /**
     * Создает заголовки для HTTP-запроса с токеном авторизации.
     *
     * @param token Токен авторизации.
     * @return Заголовки для HTTP-запроса.
     */
    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
