package com.dustdawn.mapper;

import com.dustdawn.entity.User;

public interface UserMapper {
    public User findByName(String name);
    public User findById(Integer id);
}