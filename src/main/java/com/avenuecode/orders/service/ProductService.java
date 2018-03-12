package com.avenuecode.orders.service;

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/*
 * Service that helps API do its thing
 */

@Service
public class ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String productId) {
        return productRepository.findOne(productId);
    }
    
    public List<Product> listProductBySpecification(Specification<Product> specification) {
        return productRepository.findAll(specification);
    }

}
