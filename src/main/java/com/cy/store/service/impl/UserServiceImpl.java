package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.selectByUsername(username);

        if (result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        // 密码加密处理
        // salt + password + salt -> md5算法 3 times（salt是一个随机的串）
        // 随机生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        String password = user.getPassword();
        String md5Password = getMD5Password(password, salt);

        user.setPassword(md5Password);
        user.setSalt(salt);

        // 补全数据：isDeleted字段设置为0
        user.setIsDelete(0);
        // 补全数据：4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());

        Date date = new Date();

        user.setCreatedTime(date);
        user.setModifiedTime(date);

        Integer rows = userMapper.insert(user);

        if (rows != 1) {
            throw new InsertException("用户注册过程中产生未知异常");
        }
    }

    /**
     * 定义md5算法的加密处理
     */
    private String getMD5Password(String password, String salt) {
        String result = password;

        for (int i = 0; i < 3; i++) {
            result = DigestUtils.md5DigestAsHex((salt + result + salt).getBytes()).toUpperCase();
        }

        return result;
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.selectByUsername(username);

        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }

        String md5Password = result.getPassword();
        String salt = result.getSalt();

        if (!Objects.equals(md5Password, getMD5Password(password, salt))) {
            throw new PasswordNotMatchedException("用户密码错误");
        }

        // 减少传递的字段，提升性能
        User user = new User();

        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.selectByUid(uid);

        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        User user = new User();

        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User user = userMapper.selectByUid(uid);

        if (uid == null || user.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        String oldMD5Password = getMD5Password(oldPassword, user.getSalt());

        if (!Objects.equals(oldMD5Password, user.getPassword())) {
            throw new PasswordNotMatchedException("密码错误");
        }

        String newMD5Password = getMD5Password(newPassword, user.getSalt());

        Integer rows = userMapper.updatePasswordByUid(user.getUid(), newMD5Password, username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public void changeInfo(Integer uid, String modifiedUsername, User user) {
        User result = userMapper.selectByUid(uid);

        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        user.setUid(uid);
        user.setModifiedUser(modifiedUsername);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);

        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.selectByUid(uid);

        if (result == null || Objects.equals(result.getIsDelete(), 1)) {
            throw new UserNotFoundException("用户数据不存在");
        }

        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }
}
