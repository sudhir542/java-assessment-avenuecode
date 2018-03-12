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
import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.repository.OrderRepository;
import com.avenuecode.orders.specification.OrderSpecification;
import com.avenuecode.orders.util.SearchCriteria;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = OrdersApplication.class)
@ActiveProfiles("orders-test")
public class OrderSpecificationTest {
    @Autowired
    private OrderRepository orderRepository;

    private Product firstProduct;
    private Product secondProduct;
    private Order firstOrder;
    private Order secondOrder;

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

        secondProduct = new Product();
        secondProduct.setUpc("1358743283");
        secondProduct.setSku("7394650110003");
        secondProduct.setProductId("2");
        secondProduct.setPrice(new BigDecimal(39.99));
        secondProduct.setDescription("Diva Jeans");
        products.add(secondProduct);

        firstOrder = new Order();
        firstOrder.setOrderId("1");
        firstOrder.setStatus("SHIPPED");
        firstOrder.setOrderNumber("RTL_1001");
        firstOrder.setDiscount(new BigDecimal(15.15));
        firstOrder.setTaxPercent(new BigDecimal(10));
        //firstOrder.setTotal(new BigDecimal(59.98));
        //firstOrder.setGrandTotal(new BigDecimal(65.97));
        //firstOrder.setTotalTax(new BigDecimal(5.99));
        firstOrder.setProducts(products);
        orderRepository.save(firstOrder);
        
        secondOrder = new Order();
        secondOrder.setOrderId("2");
        secondOrder.setStatus("FulFilled");
        secondOrder.setOrderNumber("RTL_1002");
        secondOrder.setDiscount(new BigDecimal(0));
        secondOrder.setTaxPercent(new BigDecimal(10));
        //secondOrder.setTotal(new BigDecimal(59.98));
        //secondOrder.setGrandTotal(new BigDecimal(65.97));
        //secondOrder.setTotalTax(new BigDecimal(5.99));
        secondOrder.setProducts(products);
        orderRepository.save(secondOrder);
    }

    @Test
    public void returnOrderStatusShipped() {
        OrderSpecification orderSpecification =
                new OrderSpecification(new SearchCriteria("status", ":", "shipped"));

        List<Order> results = orderRepository.findAll(orderSpecification);

        assert results.size()> 0;
    }

    @Test
    public void returnNullForOrderStatusNotShipped() {
        OrderSpecification orderSpecification =
                new OrderSpecification(new SearchCriteria("status", ":", "NotShipped"));

        List<Order> results = orderRepository.findAll(orderSpecification);

        assert results.size() == 0;
    }

    @Test
    public void returnOrderWithDiscount(){
        OrderSpecification orderSpecification =
                new OrderSpecification(new SearchCriteria("discount", ">", "0"));

        List<Order> results = orderRepository.findAll(orderSpecification);
        assert results.size() > 0;
    }

    @Test
    public void returnOrderWithoutDiscount(){
        OrderSpecification orderSpecification =
                new OrderSpecification(new SearchCriteria("discount", "<", "0"));

        List<Order> results = orderRepository.findAll(orderSpecification);
        assert results.size() == 0;
    }
    
    @Test
    public void returnOrderWithProductSizeGreaterThan2(){
        OrderSpecification orderSpecification =
                new OrderSpecification(new SearchCriteria("products", ">", "2"));

        List<Order> results = orderRepository.findAll(orderSpecification);
        assert results.size() == 0;
    }
    
    @Test
    public void returnOrderWithProductSizeLessThan2(){
        OrderSpecification orderSpecification =
                new OrderSpecification(new SearchCriteria("products", "<", "2"));

        List<Order> results = orderRepository.findAll(orderSpecification);
        assert results.size() > 0;
    }
}
