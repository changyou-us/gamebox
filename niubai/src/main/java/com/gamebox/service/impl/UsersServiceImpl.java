package com.gamebox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamebox.dao.UsersDao;
import com.gamebox.model.Users;
import com.gamebox.service.UsersService;

@Service("usersServiceImpl")
public class UsersServiceImpl implements UsersService{
    
    @Autowired
    private UsersDao usersDao;

    @Override
    public void save(Users users) {

        
        System.out.println(usersDao.insert(users));
        System.out.println(users.getUserId());
    }


    @Override
    public Users findUserByUserId(String userId) {

        return usersDao.findUserByUserId(userId);
    }


    @Override
    public Users findUserByEmail(String email) {

        return usersDao.findUserByEmail(email);
    }

    

}
