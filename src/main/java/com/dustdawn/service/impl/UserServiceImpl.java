package com.dustdawn.service.impl;

import com.dustdawn.entity.User;
import com.dustdawn.mapper.UserMapper;
import com.dustdawn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }
}
