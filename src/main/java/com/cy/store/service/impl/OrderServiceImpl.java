package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.AddressService;
import com.cy.store.service.CartService;
import com.cy.store.service.OrderService;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(Integer aid, Integer uid, String username, Integer[] cids) {
        List<CartVO> list = cartService.getVOByCids(uid, cids);
        Long totalPrice = 0L;

        for (CartVO cartVO : list) {
            totalPrice += cartVO.getRealPrice() * cartVO.getNum();
        }

        Address address = addressService.getByAidAndUid(aid, uid);
        Order order = new Order();

        order.setUid(uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());

        order.setStatus(0);
        order.setTotalPrice(totalPrice);

        order.setOrderTime(new Date());
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());

        Integer rows = orderMapper.insertOrder(order);

        if (rows != 1) {
            throw new RuntimeException("创建订单异常");
        }

        for (CartVO cartVO : list) {
            OrderItem orderItem = new OrderItem();

            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setNum(cartVO.getNum());
            orderItem.setPrice(cartVO.getRealPrice());

            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());

            rows = orderMapper.insertOrderItem(orderItem);

            if (rows != 1) {
                throw new RuntimeException("创建订单项异常");
            }
        }

        return order;
    }
}
