package com.avenuecode.orders.repository;

import com.avenuecode.orders.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.io.Serializable;

/**
 * Repository can be used to delegate CRUD operations 
 * against the data source here in this case its Product
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Serializable>,JpaSpecificationExecutor<Product>  {
	
}
