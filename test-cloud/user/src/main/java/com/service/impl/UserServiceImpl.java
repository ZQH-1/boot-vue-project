package com.service.impl;

import com.mapper.UserMapper;
import com.service.UserService;
import entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Override
    public User getUserByUid(int uid) {
        return userMapper.getUserByUid(uid);
    }
}
