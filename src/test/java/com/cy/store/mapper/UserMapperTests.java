package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
// @RunWith：表示启动这个单元测试类（否则无法运行），需要传递一个SpringRunner实例类型的参数
@RunWith(SpringRunner.class)
public class UserMapperTests {
    @Autowired
    private UserMapper mapper;

    @Test
    public void add() {
        User user = new User();
        user.setUsername("Asuka");
        user.setPassword("123");
        Integer row = mapper.insert(user);
        System.out.println(row);
    }

    @Test
    public void findByUsername() {
        User user = mapper.selectByUsername("Tim");
        System.out.println(user.toString());
    }

    @Test
    public void savePasswordByUid() {
        mapper.updatePasswordByUid(17, "123", "yin", new Date());
    }

    @Test
    public void changeInfo() {
        User user = mapper.selectByUsername("ajax");

        user.setEmail("ajax@gmail.com");
        user.setPhone("18012345679");
        user.setGender(1);
        Integer rows = mapper.updateInfoByUid(user);
        System.out.println(rows);
    }


}
