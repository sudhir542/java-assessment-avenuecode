package com.avenuecode.orders.resource;

import com.avenuecode.orders.domain.Order;
import com.avenuecode.orders.exception.ResourceNotFoundException;
import com.avenuecode.orders.service.OrderService;
import com.avenuecode.orders.specification.OrderSpecification;
import com.avenuecode.orders.util.SearchCriteria;
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
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import static org.springframework.http.ResponseEntity.ok;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/*
 * Demonstrates how to set up RESTful API endpoints for /orders
 */

@RestController
@RequestMapping("/orders")
@Api(tags = {"orders"})
public class OrderResource extends AbstractRestHandler{

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "",
        method = RequestMethod.GET,
        produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List of all orders also search based on order request params", notes = "This endpoint is used to list all of the orders "
    		+ " http://localhost:8088/orders and one can search based on the parameters too like "
    		+ " for example "
    		+ " http://localhost:8088/orders?search=status:shipped "
    		+ " http://localhost:8088/orders?search=discount>0 etc.")
    public ResponseEntity<List<Order>> listOrders() {
        return ok(orderService.listOrders());
    }

    @RequestMapping(value = "/{orderId}",
        method = RequestMethod.GET,
        produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a order details", notes = "This endpoint is used to get information about specific order.")
    public ResponseEntity<Order> getOrder(@ApiParam(value = "The ID of the existing order resource.", required = true)
    @PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        checkResourceFound(order);
        return ok(order);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    /*
     * Search based on request params.
     * This method is used to search on orders with parameters like status, products, discount etc. ")
     */
    public ResponseEntity<List<Order>> search(@RequestParam(value = "search", defaultValue = "") String search) {
        if (search == null || search.trim().isEmpty()) {
            return ok(orderService.listOrders());
        }
        SearchCriteria searchCriteria = null;
        Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+)");
        Matcher matcher = pattern.matcher(search);
        while (matcher.find()) {
            if (matcher.group(1).equalsIgnoreCase("products")) {
                List<Order> orderResults = getOrderForProductsCriteria(orderService.listOrders(), matcher.group(3));
                return ok(orderResults);
            }
            searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        if (searchCriteria == null) {
        		throw new ResourceNotFoundException("Resource not found.");
        }

        OrderSpecification orderSpecification = new OrderSpecification(searchCriteria);
        Specifications<Order> result = Specifications.where(orderSpecification);

        List<Order> orderResults = orderService.listOrderBySpecification(result);

        if (orderResults == null || orderResults.isEmpty()) {
        		throw new ResourceNotFoundException("Resource not found.");
        }

        return ok(orderResults);
    }

    /**
     * get order for product criteria
     * @param orders
     * @param value
     * @return
     */
    private List<Order> getOrderForProductsCriteria(List<Order> orders, String value) {
        List<Order> resultOrder = new ArrayList<Order>();
        int minimumProductSizeLimit = Integer.parseInt(value);
        for (Order order : orders) {
            if (order.getProducts().size() > minimumProductSizeLimit) {
                resultOrder.add(order);
            }
        }
        return resultOrder;
    }
}
