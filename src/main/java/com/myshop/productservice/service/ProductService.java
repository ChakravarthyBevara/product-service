package com.myshop.productservice.service;

import com.myshop.productservice.entity.Product;
import com.myshop.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public void saveAll(List<Product> products) {
        repository.saveAll(products);
    }


    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void reduceStock(Long id, int quantity) {
        Product product = getProductById(id);
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        product.setStock(product.getStock() - quantity);
        repository.save(product);
    }

    public Product increaseStock(Long id, int quantity) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStock(product.getStock() + quantity);
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

}
