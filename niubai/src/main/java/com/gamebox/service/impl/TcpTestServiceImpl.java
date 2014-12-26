/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV Project:niubai
 * Package:com.gamebox.service.impl File:TcpTestServiceImpl.java Date:2014年12月16日
 */
package com.gamebox.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gamebox.dao.TcpTestDao;
import com.gamebox.model.TcpTest;
import com.gamebox.service.TcpTestService;

/**
 * @author wuji
 * 
 */
@Service("testService")
public class TcpTestServiceImpl implements TcpTestService {

    public TcpTestDao getTcpTestDao() {

        return tcpTestDao;
    }

    @Autowired
    public void setTcpTestDao(TcpTestDao tcpTestDao) {

        this.tcpTestDao = tcpTestDao;
    }

    private TcpTestDao tcpTestDao;

    @Cacheable(value = "test")
    public List<TcpTest> getAll() {

        return tcpTestDao.getAll();
    }

    @CacheEvict(value = "test", key="0")
    public void updateById(TcpTest test) {

        tcpTestDao.updateById(test);
    }

}
