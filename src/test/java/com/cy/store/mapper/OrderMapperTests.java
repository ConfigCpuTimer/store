package com.cy.store.mapper;

import com.cy.store.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTests {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertOrder() {
        Order order = new Order();
        order.setUid(7);
        order.setRecvName("张三");
        order.setRecvPhone("13888888888");
        order.setRecvProvince("江苏");
        order.setRecvCity("南京");
        order.setRecvArea("玄武区");
        order.setRecvAddress("玄武大道");
        order.setTotalPrice(new Long(100));
        order.setStatus(1);
        order.setOrderTime(new Date());
        order.setPayTime(new Date());

        Integer rows = orderMapper.insertOrder(order);

        System.err.println(rows);
    }
}
