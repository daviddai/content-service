package com.micro.services.product.content.svc.dao;

import com.micro.services.product.content.svc.dao.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Product product) {
        final String ADD_PRODUCT = "insert into product values (?, ?, ?)";
        jdbcTemplate.update(ADD_PRODUCT,
                product.getProductCode(), product.getProductName(), product.getProductDescription());
    }

    public Optional<Product> find(String productCode) {
        final String FIND_BY_PRODUCT_CODE = "select * from product where product_code = ?";
        Product product =
                jdbcTemplate.queryForObject(FIND_BY_PRODUCT_CODE, new Object[]{productCode}, new ProductRowMapper());
        return Optional.ofNullable(product);
    }

    public List<Product> getAll() {
        return Collections.emptyList();
    }

    private class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                    rs.getString("product_code"),
                    rs.getString("product_name"),
                    rs.getString("product_description")
            );
        }
    }

}
