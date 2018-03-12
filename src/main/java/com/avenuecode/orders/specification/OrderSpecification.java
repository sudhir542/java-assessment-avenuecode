package com.avenuecode.orders.specification;

import com.avenuecode.orders.domain.Order;
import org.springframework.data.jpa.domain.Specification;
import com.avenuecode.orders.util.SearchCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/*
 * This OrderSpecification helps to implement Search Criteria in Order
 */

public class OrderSpecification implements Specification<Order> {
    private SearchCriteria searchCriteria;

    /*
     * Constructor for OrderSpecification
     */
    public OrderSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

   /*
    * Override the toPredicate and implement the logic for search here.
    */
    
    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThan(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        if (searchCriteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        if (searchCriteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(root.<String>get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString().toUpperCase() + "%");
            } else {
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            }
        }
        return null;
    }
}