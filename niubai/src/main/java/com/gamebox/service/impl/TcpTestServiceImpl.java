/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV Project:niubai
 * Package:com.gamebox.service.impl File:TcpTestServiceImpl.java Date:2014年12月16日
 */
package com.gamebox.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.gamebox.dao.DirectPaymentOrderDao;
import com.gamebox.dao.FacebookAppInformationDao;
import com.gamebox.dao.FacebookUserBusinessDao;
import com.gamebox.dao.GameDao;
import com.gamebox.dao.GamePaymentTypePriceDao;
import com.gamebox.dao.LoginGameHistoryDao;
import com.gamebox.dao.ServerDao;
import com.gamebox.dao.TcpTestDao;
import com.gamebox.dao.UsersDao;
import com.gamebox.model.TcpTest;
import com.gamebox.model.Users;
import com.gamebox.service.ServerService;
import com.gamebox.service.TcpTestService;

/**
 * @author wuji
 * 
 */
@Service
public class TcpTestServiceImpl implements TcpTestService {
    
    @Autowired
    private DirectPaymentOrderDao directPaymentOrderDao;
    
    @Autowired
    private FacebookAppInformationDao facebookAppInformationDao;
    
    @Autowired
    private FacebookUserBusinessDao facebookUserBusinessDao;
    
    @Autowired
    private GameDao fameDao;
    
    @Autowired
    private GamePaymentTypePriceDao gamePaymentTypePriceDao;
    
    @Autowired
    private LoginGameHistoryDao loginGameHistoryDao;
    
    @Autowired
    private ServerDao serverDao;
    
    @Autowired
    private UsersDao usersDao;
    
    @Autowired
    private TcpTestDao tcpTestDao;
    
    @Autowired
    private ServerService serverService;

    

    public List<TcpTest> getAll() {

        return tcpTestDao.getAll();
    }

    @CacheEvict(value = "test", key="0")
    public void updateById(TcpTest test) {

        tcpTestDao.updateById(test);
    }

    @Override
    public void test() {
        
        List<String> emailsList = new ArrayList<String>();
        emailsList.add("maomi");
        emailsList.add("wahahaha");
        Users user = usersDao.findUserByEmailsList(emailsList);
        System.out.println(user.getUserId());
    }

    

}
