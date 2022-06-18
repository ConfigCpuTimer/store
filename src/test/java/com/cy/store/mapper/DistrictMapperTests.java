package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictMapperTests {
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void selectByParent() {
        List<District> Districts = districtMapper.selectByParent("210100");

        for (District district : Districts) {
            System.out.println(district);
        }
    }

    @Test
    public void selectNameByCode() {
        String name = districtMapper.selectNameByCode("610000");

        System.err.println(name);
    }
}
