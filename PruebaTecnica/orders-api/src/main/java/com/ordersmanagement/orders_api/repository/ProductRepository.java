package com.ordersmanagement.orders_api.repository;

import com.ordersmanagement.orders_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
