package com.server.store.repositories;


import com.server.store.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для управления заказами.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}