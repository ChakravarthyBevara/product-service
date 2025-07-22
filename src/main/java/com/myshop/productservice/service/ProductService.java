package com.myshop.productservice.service;

import com.myshop.productservice.entity.Product;
import com.myshop.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product saveProduct(Product product) {
        logger.info("Saving new product: {}", product.getName());
        return repository.save(product);
    }

    public void saveAll(List<Product> products) {
        logger.info("Saving list of products. Total: {}", products.size());
        repository.saveAll(products);
    }

    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        logger.info("Fetching product with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product with ID {} not found", id);
                    return new RuntimeException("Product not found");
                });
    }

    @Transactional
    public void reduceStock(Long id, int quantity) {
        logger.info("Reducing stock for Product ID: {} by quantity: {}", id, quantity);
        Product product = getProductById(id);
        if (product.getStock() < quantity) {
            logger.error("Insufficient stock for Product ID: {}. Available: {}, Requested: {}", id, product.getStock(), quantity);
            throw new RuntimeException("Insufficient stock");
        }
        product.setStock(product.getStock() - quantity);
        repository.save(product);
        logger.info("Stock updated for Product ID: {}. Remaining stock: {}", id, product.getStock());
    }
    @Transactional
    public Product increaseStock(Long id, int quantity) {
        logger.info("Increasing stock for Product ID: {} by quantity: {}", id, quantity);
        Product product = repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product with ID {} not found", id);
                    return new RuntimeException("Product not found");
                });
        product.setStock(product.getStock() + quantity);
        logger.info("Stock updated for Product ID: {}. New stock: {}", id, product.getStock());
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        repository.deleteById(id);
        logger.info("Product with ID: {} deleted successfully", id);
    }
}
