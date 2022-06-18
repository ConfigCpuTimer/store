package com.cy.store.controller;

import com.cy.store.entity.Product;
import com.cy.store.service.ProductService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> data = productService.getBestFourSales();

        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/{id}/details")
    public JsonResult<Product> getById(@PathVariable Integer id) {
        Product product = productService.getById(id);

        return new JsonResult<>(OK, product);
    }
}
