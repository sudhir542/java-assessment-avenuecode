package com.avenue.code.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.orders.OrdersApplication;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.ProductRepository;
import com.avenuecode.orders.specification.ProductSpecification;
import com.avenuecode.orders.util.SearchCriteria;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = OrdersApplication.class)
@ActiveProfiles("orders-test")
public class ProductSpecificationTest {

    @Autowired
    private ProductRepository productRepository;

    private Product firstProduct;
    private Product secondProduct;

    @Before
    public void init() {

        List<Product> products = new ArrayList<Product>();

        firstProduct = new Product();
        firstProduct.setUpc("1257833283");
        firstProduct.setSku("9394550220002");
        firstProduct.setProductId("1");
        firstProduct.setPrice(new BigDecimal(39.99));
        firstProduct.setDescription("Diva Jeans");
        products.add(firstProduct);
        productRepository.save(firstProduct);

        secondProduct = new Product();
        secondProduct.setUpc("1358743283");
        secondProduct.setSku("7394650110003");
        secondProduct.setProductId("2");
        secondProduct.setPrice(new BigDecimal(39.99));
        secondProduct.setDescription("Diva Jeans");
        products.add(secondProduct);
        productRepository.save(secondProduct);
    }

    @Test
    public void returnProductBasedOnPriceGreaterThan30(){

        ProductSpecification productSpecification =
                new ProductSpecification(new SearchCriteria("price", ">", "30"));
        List<Product> results = productRepository.findAll(productSpecification);

        assert results.size() > 0;
    }
    
}
