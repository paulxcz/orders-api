package com.ordersmanagement.orders_api.repository;

import com.ordersmanagement.orders_api.model.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long> {
}
