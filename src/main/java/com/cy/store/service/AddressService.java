package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

public interface AddressService {
    void addNewAddress(Integer uid, String username, Address address);

    List<Address> getByUid(Integer uid);

    void setDefaultAddress(Integer aid, Integer uid, String username);

    void removeAddress(Integer aid, Integer uid, String username);

    Address getByAidAndUid(Integer aid, Integer uid);
}
