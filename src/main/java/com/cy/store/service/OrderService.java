package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;

public interface OrderService {
    Order createOrder(Integer aid, Integer uid, String username, Integer[] cids);
}
