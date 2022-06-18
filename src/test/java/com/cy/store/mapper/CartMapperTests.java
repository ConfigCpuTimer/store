package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(7);
        cart.setPid(10000011);
        cart.setNum(2);
        cart.setPrice(1000L);
        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid() {
        cartMapper.updateNumByCid(1, 4, "test", new Date());
    }

    @Test
    public void selectByUidAndPid() {
        Cart cart = cartMapper.selectByUidAndPid(7, 10000011);
        System.err.println(cart);
    }

    @Test
    public void selectVOByUid() {
        List<CartVO> list = cartMapper.selectVOByUid(7);
        System.err.println(list);
    }

    @Test
    public void selectByCid() {
        Cart cart = cartMapper.selectByCid(1);
        System.err.println(cart);
    }

    @Test
    public void selectVOByCids() {
        List<CartVO> list = cartMapper.selectVOByCids(new Integer[]{1, 2});
        System.err.println(list);
    }
}
