package com.myshop.productservice.controller;

import com.myshop.productservice.entity.Product;
import com.myshop.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {

        return service.saveProduct(product);
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<String> createProducts(@RequestBody List<Product> products) {
        service.saveAll(products);
        return ResponseEntity.ok("Products created successfully.");
    }


    @GetMapping

    public List<Product> getAll() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PutMapping("/{id}/reduce-stock")
    public void reduceStock(@PathVariable Long id, @RequestParam int quantity) {
        service.reduceStock(id, quantity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }

}

