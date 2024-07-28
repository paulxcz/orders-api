package com.ordersmanagement.orders_api.controller;

import com.ordersmanagement.orders_api.model.Order;
import com.ordersmanagement.orders_api.model.Product;
import com.ordersmanagement.orders_api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        for (Product product : order.getProducts()) {
            product.setOrder(order); // Set the order reference in each product
        }
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if ("Completed".equals(order.getStatus())) {
                return ResponseEntity.badRequest().body(null);
            }
            order.setOrderNumber(orderDetails.getOrderNumber());
            order.setDate(orderDetails.getDate());
            order.setNumProducts(orderDetails.getNumProducts());
            order.setFinalPrice(orderDetails.getFinalPrice());
            order.setStatus(orderDetails.getStatus());

            // Clear existing products and set new products
            order.getProducts().clear();
            for (Product product : orderDetails.getProducts()) {
                product.setOrder(order); // Set the order reference in each product
                order.getProducts().add(product);
            }

            return ResponseEntity.ok(orderRepository.save(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if ("Completed".equals(order.getStatus())) {
                return ResponseEntity.badRequest().build();
            }
            orderRepository.delete(order);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
