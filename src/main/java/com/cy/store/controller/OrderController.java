package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.service.OrderService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public JsonResult<Order> createOrder(/*@PathVariable("aid") */Integer aid, /*@PathVariable("cids") */Integer[] cids, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order order = orderService.createOrder(aid, uid, username, cids);
        return new JsonResult<Order>(OK, order);
    }
}
