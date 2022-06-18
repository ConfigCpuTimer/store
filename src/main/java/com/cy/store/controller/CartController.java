package com.cy.store.controller;

import com.cy.store.service.CartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, /*String username, */HttpSession session) {
        cartService.addToCart(getUidFromSession(session), pid, amount, getUsernameFromSession(session));

        return new JsonResult<>(OK);
    }

    @RequestMapping({"/", ""})
    public JsonResult<List<CartVO>> getVOByUid(/*Integer pid, Integer amount, */HttpSession session) {
        List<CartVO> result = cartService.getVOByUid(getUidFromSession(session));

        return new JsonResult<>(OK, result);
    }

    @RequestMapping("/{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.addNum(cid, getUidFromSession(session), getUsernameFromSession(session));

        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        List<CartVO> result = cartService.getVOByCids(getUidFromSession(session), cids);

        return new JsonResult<>(OK, result);
    }
}
