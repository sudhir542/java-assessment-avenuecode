package com.avenuecode.orders.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

/*
 * A simple domain entity product
 */

@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JsonIgnore
    private String productId;

    @Column(unique = true, nullable = false, length = 10)
    private String upc;

    @Column(unique = true, nullable = false, length = 13)
    private String sku;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
