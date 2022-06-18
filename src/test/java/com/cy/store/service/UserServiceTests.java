package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    public void reg() {
        try {
            User user = new User();

            user.setUsername("Tim");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("test", "321");

        System.out.println(user.getUsername());
    }

    @Test
    public void changePassword() {
        userService.changePassword(7, "test", "123", "321");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(5).getUsername());
    }

    @Test
    public void changeInfo() {
        User user = new User();

        user.setPhone("18812345678");
        user.setEmail("test@example.com");
        user.setGender(0);

        userService.changeInfo(5, "Admin", user);
    }
}
