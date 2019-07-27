package com.dustdawn.service;

import com.dustdawn.entity.User;

public interface UserService {

    public User findByName(String name);
    public User findById(Integer id);


}
