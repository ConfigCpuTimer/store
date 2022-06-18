package com.cy.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void createOrder() {
        Integer[] cids = {3, 5};
        orderService.createOrder(16, 7, "admin", cids);
    }
}
