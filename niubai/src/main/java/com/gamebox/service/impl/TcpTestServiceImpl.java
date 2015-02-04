/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV Project:niubai
 * Package:com.gamebox.service.impl File:TcpTestServiceImpl.java Date:2014年12月16日
 */
package com.gamebox.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import com.gamebox.model.DirectPaymentOrder;
import com.gamebox.model.DisplayType;
import com.gamebox.model.FacebookUserBusiness;
import com.gamebox.model.OpenStatusType;
import com.gamebox.model.TcpTest;
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

    

    @Cacheable(value = "test")
    public List<TcpTest> getAll() {

        return tcpTestDao.getAll();
    }

    @CacheEvict(value = "test", key="0")
    public void updateById(TcpTest test) {

        tcpTestDao.updateById(test);
    }

    @Override
    public void test() {

        System.out.println(serverDao.serverExists(1, 14, OpenStatusType.able, DisplayType.display));
    }

    @Override
    @Cacheable("ksd")
    public String cache() {

        // TODO Auto-generated method stub
        return UUID.randomUUID().toString();
    }
    
    @Override
    @Cacheable("frank")
    public String cache1() {

        // TODO Auto-generated method stub
        return UUID.randomUUID().toString();
    }

}
