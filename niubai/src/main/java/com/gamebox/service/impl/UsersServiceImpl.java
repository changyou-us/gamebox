package com.gamebox.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamebox.dao.UsersDao;
import com.gamebox.model.Users;
import com.gamebox.service.UsersService;

@Service("usersServiceImpl")
public class UsersServiceImpl implements UsersService{

    private UsersDao usersDao;
    
    
    /**
     * @return the usersDao
     */
    public UsersDao getUsersDao() {
    
        return usersDao;
    }

    
    /**
     * @param usersDao the usersDao to set
     */
    @Autowired
    public void setUsersDao(UsersDao usersDao) {
    
        this.usersDao = usersDao;
    }

    @Override
    public void save(Users users) {

        usersDao.insert(users);
    }


    @Override
    public List<Users> queryAll() {

        return usersDao.selectAll();
    }

    

}
