package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(7);
        address.setPhone("18812345678");
        address.setName("Jiang");

        Integer row = addressMapper.insert(address);

        System.out.println(row);
    }

    @Test
    public void findByUid() {
        List<Address> addresses = addressMapper.selectByUid(7);

        for (Address address : addresses) {
            System.err.println(address);
        }
    }

    @Test
    public void deleteByAid() {
        addressMapper.deleteByAid(1);
    }

    @Test
    public void selectLastModifiedByUid() {
        System.err.println(addressMapper.selectLastModifiedByUid(7));
    }


}
