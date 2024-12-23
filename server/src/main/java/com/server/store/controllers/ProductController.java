package com.server.store.controllers;

import com.server.store.DTO.ProductDTO;
import com.server.store.config.UserDetailsImpl;
import com.server.store.exceptions.ResourceNotFoundException;
import com.server.store.models.Product;
import com.server.store.repositories.ProductRepository;
import com.server.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Контроллер для управления продуктами.
 * Предоставляет функциональность для добавления, обновления, получения и удаления продуктов.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Получает список всех продуктов.
     *
     * @return Список всех продуктов
     */
    @GetMapping("/allProducts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_CUSTOMER')")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Добавляет новый продукт в систему.
     *
     * @param product Объект продукта, который нужно добавить
     * @return Ответ с добавленным продуктом и статусом CREATED
     */
    @PostMapping("/addProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setCreatedBy(userDetails.getUser().getId()); // Устанавливаем владельца записи

        Product savedTruck = productService.createProduct(product);
        return new ResponseEntity<>(savedTruck, HttpStatus.CREATED);
    }

    /**
     * Обновляет информацию о продукте.
     *
     * @param id Идентификатор продукта, который нужно обновить
     * @param productDTO Объект с новыми данными для обновления
     * @return Обновленный объект продукта
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        // Преобразуем ProductDTO в Product
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(productDTO.getCategory());

        // Обновляем продукт
        return productService.updateProduct(id, product);
    }

    /**
     * Удаляет продукт по идентификатору.
     *
     * @param id Идентификатор продукта, который нужно удалить
     * @return Ответ с пустым телом и статусом OK
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
