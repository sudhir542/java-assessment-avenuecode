package com.avenuecode.orders.resource;

import com.avenuecode.orders.domain.Product;
import com.avenuecode.orders.exception.ResourceNotFoundException;
import com.avenuecode.orders.service.ProductService;
import com.avenuecode.orders.specification.ProductSpecification;
import com.avenuecode.orders.util.SearchCriteria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static org.springframework.http.ResponseEntity.ok;

/*
 * Demonstrates how to set up RESTful API endpoints for /products
 */

@RestController
@RequestMapping("/products")
@Api(tags = {"products"})
public class ProductResource extends AbstractRestHandler{

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List of all products, Search request params", notes = "This endpoint is used to list all of the products "
    		+ " http://localhost:8088/products "
    		+ " and one can search based on the parameters too like "
    		+ " http://localhost:8088/products?search=price>30 etc.")
    public ResponseEntity<List<Product>> listProducts() {
        return ok(productService.listProducts());
    }

    @RequestMapping(value = "/{productId}",
        method = RequestMethod.GET,
        produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a product details", notes = "This endpoint is used to get information about specific product.")
    public ResponseEntity<Product> getProduct(@ApiParam(value = "The ID of the existing product resource.", required = true) 
    @PathVariable String productId) {
        Product product = productService.getProduct(productId);
        checkResourceFound(product);
        return ok(product);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    /* Search based on request params.
     * This below method is used to search on products with parameters like price etc. ")
     */
    public ResponseEntity<List<Product>> search(@RequestParam(value = "search", defaultValue = "") String search) {
        if (search == null || search.trim().isEmpty()) {
            return ok(productService.listProducts());
        }
        SearchCriteria searchCriteria = null;
        Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+)");
        Matcher matcher = pattern.matcher(search);
        while (matcher.find()) {
            searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        if (searchCriteria == null) {
        		throw new ResourceNotFoundException("Resource not found.");
        }

        ProductSpecification productSpecification = new ProductSpecification(searchCriteria);
        Specifications<Product> result = Specifications.where(productSpecification);

        List<Product> orderResults = productService.listProductBySpecification(result);

        if (orderResults == null || orderResults.isEmpty()) {
        		throw new ResourceNotFoundException("Resource not found.");
        }

        return ok(orderResults);
    }
}

