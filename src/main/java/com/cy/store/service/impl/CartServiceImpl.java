package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.CartService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 購物車的業務層依賴於購物車持久層和商品持久層
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart res = cartMapper.selectByUidAndPid(uid, pid);

        if (res == null) { // 表示購物車現無此商品
            Cart cart = new Cart();
            Product product = productMapper.selectById(pid);

            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            cart.setPrice(product.getPrice());
            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());

            Integer rows = cartMapper.insert(cart);

            if (rows != 1) {
                throw new InsertException("添加到購物車時產生未知異常");
            }
        } else {
            Integer num = res.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(res.getCid(), num, username, new Date());

            if (rows != 1) {
                throw new UpdateException("更新購物車時產生未知異常");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.selectVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.selectByCid(cid);

        if (result == null) {
            throw new CartNotFoundException("找不到購物車數據");
        }

        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("您沒有權限操作此購物車數據");
        }

        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新購物車時產生未知異常");
        }

        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> cartVOs = cartMapper.selectVOByCids(cids);
        Iterator<CartVO> iterator = cartVOs.iterator();

        while (iterator.hasNext()) {
            CartVO cartVO = iterator.next();

            if (!cartVO.getUid().equals(uid)) {
                iterator.remove();
            }
        }

        return cartVOs;
    }
}
