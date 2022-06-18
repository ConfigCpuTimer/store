package com.cy.store.service;

import com.cy.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {
    @Autowired
    private ProductService productService;

    @Test
    public void getBestFourSales() {
        List<Product> products = productService.getBestFourSales();

        for (Product product : products) {
            System.err.println(product);
        }
    }
}
