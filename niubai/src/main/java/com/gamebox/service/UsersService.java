package com.gamebox.service;

import com.gamebox.model.Users;

public interface UsersService {
    
    public void save(Users users);
    
    Users findUserByUserId(String userId);

    Users findUserByEmail(String email);
}
