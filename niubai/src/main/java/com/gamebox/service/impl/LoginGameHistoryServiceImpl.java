/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV
 * Project:new_gamebox_trunk Package:com.gamebox.service.impl File:LoginGameHistoryServiceImpl.java Date:2014年8月4日
 */
package com.gamebox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamebox.dao.LoginGameHistoryDao;
import com.gamebox.model.LoginGameHistory;
import com.gamebox.service.LoginGameHistoryService;

/**
 * Service - 管理员
 * 
 * @author Dick niu
 * @version 1.0
 */
@Service
public class LoginGameHistoryServiceImpl implements LoginGameHistoryService {

    @Autowired
    private LoginGameHistoryDao loginGameHistoryDao;

    public void save(LoginGameHistory loginGameHistory) {

        loginGameHistoryDao.insert(loginGameHistory);
    }

    @Override
    public int findByUserIdAndGameId(Integer userId, Integer gameId) {

        return loginGameHistoryDao.findByUserIdAndGameId(userId, gameId);
    }
}
