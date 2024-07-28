package com.ordersmanagement.orders_api.controller;

import com.ordersmanagement.orders_api.model.ProductCatalog;
import com.ordersmanagement.orders_api.repository.ProductCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-catalog")
public class ProductCatalogController {

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @GetMapping
    public List<ProductCatalog> getAllProducts() {
        return productCatalogRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCatalog> getProductById(@PathVariable Long id) {
        Optional<ProductCatalog> product = productCatalogRepository.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductCatalog createProduct(@RequestBody ProductCatalog product) {
        return productCatalogRepository.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCatalog> updateProduct(@PathVariable Long id, @RequestBody ProductCatalog productDetails) {
        Optional<ProductCatalog> productOptional = productCatalogRepository.findById(id);

        if (productOptional.isPresent()) {
            ProductCatalog product = productOptional.get();
            product.setName(productDetails.getName());
            product.setUnitPrice(productDetails.getUnitPrice());
            return ResponseEntity.ok(productCatalogRepository.save(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productCatalogRepository.existsById(id)) {
            productCatalogRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
