package com.micro.services.product.content.svc.dao;

import com.micro.services.product.content.svc.dao.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Product product) {
        final String ADD_PRODUCT = "insert into product values (?, ?, ?)";
        jdbcTemplate.update(ADD_PRODUCT,
                product.getProductCode(), product.getProductName(), product.getProductDescription());
    }

    public Product find(String productCode) {
        return null;
    }

    public List<Product> getAll() {
        return Collections.emptyList();
    }

}
