package com.gamebox.service;

import java.util.List;

import com.gamebox.model.Users;


public interface UsersService {
    
    public void save(Users users);

    public List<Users> queryAll();
}
