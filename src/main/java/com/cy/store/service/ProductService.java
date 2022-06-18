package com.cy.store.service;

import com.cy.store.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getBestFourSales();

    Product getById(Integer id);
}
