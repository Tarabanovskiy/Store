package com.server.store.services;

import com.server.store.exceptions.ResourceNotFoundException;
import com.server.store.models.Product;
import com.server.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для управления продуктами.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Получает список всех продуктов.
     *
     * @return список всех продуктов
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Создает новый продукт.
     *
     * @param product продукт для создания
     * @return сохраненный продукт
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Обновляет информацию о продукте по идентификатору.
     *
     * @param id идентификатор продукта
     * @param productDetails обновленная информация о продукте
     * @return обновленный продукт
     * @throws ResourceNotFoundException если продукт не найден
     */
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Товар не найден"));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setCategory(productDetails.getCategory());
        return productRepository.save(product);
    }


    /**
     * Удаляет продукт по идентификатору.
     *
     * @param id идентификатор продукта
     * @throws ResourceNotFoundException если продукт не найден
     */
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Товар не найден"));
        productRepository.delete(product);
    }


}