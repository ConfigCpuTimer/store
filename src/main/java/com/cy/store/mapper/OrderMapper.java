package com.cy.store.mapper;

import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单信息
     * @param order
     * @return
     */
    Integer insertOrder(Order order);

/**
     * 插入订单项信息
     * @param orderItem
     * @return
     */
    Integer insertOrderItem(OrderItem orderItem);
}
