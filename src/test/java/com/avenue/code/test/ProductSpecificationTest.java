package com.avenue.code.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.orders.OrdersApplication;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.ProductRepository;
import com.avenuecode.orders.specification.ProductSpecification;
import com.avenuecode.orders.util.SearchCriteria;

@RunWith(SpringRunner.class)
@ActiveProfiles("orders-test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = OrdersApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.yml"
)
public class ProductSpecificationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void returnProductBasedOnPriceGreaterThan30(){

        ProductSpecification productSpecification =
                new ProductSpecification(new SearchCriteria("price", ">", "30"));
        List<Product> results = productRepository.findAll(productSpecification);

        assert results.size() > 0;
    }
    
}
