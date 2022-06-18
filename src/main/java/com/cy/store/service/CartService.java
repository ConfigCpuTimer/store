package com.cy.store.service;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;

import java.util.List;

public interface CartService {
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    List<CartVO> getVOByUid(Integer uid);

    /**
     * 更新购物车数据的数量
     * @param cid 购物车数据的id
     * @param uid 用户的id
     * @param username 操作人的用户名
     * @return 新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}
