package com.avenuecode.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/*
* This is the main Spring Boot application class. 
* It configures Spring Boot, JPA, Swagger
*/

@SpringBootApplication
public class OrdersApplication extends SpringBootServletInitializer{
	
	private static final Class<OrdersApplication> applicationClass = OrdersApplication.class;
	private static final Logger log = LoggerFactory.getLogger(applicationClass);

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
        log.info("Running orders application.");
    }
}
