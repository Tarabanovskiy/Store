package com.server.store.repositories;

import com.server.store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
/**
 * Репозиторий для управления продуктами.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
