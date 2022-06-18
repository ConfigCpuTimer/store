package com.cy.store.service;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired
    private AddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();

        address.setPhone("18812345678");
        address.setName("Kyousuke");

        addressService.addNewAddress(7, "test", address);
    }

    @Test
    public void setDefault() {
        addressService.setDefaultAddress(14, 7, "test");
    }

    @Test
    public void removeAddress() {
        addressService.removeAddress(6, 7, "test");
    }
}
