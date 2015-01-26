/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV Project:niubai
 * Package:com.gamebox.service File:TcpTestService.java Date:2014年12月16日
 */
package com.gamebox.service;

import java.util.List;

import com.gamebox.model.TcpTest;

/**
 * @author wuji
 * 
 */
public interface TcpTestService {

    List<TcpTest> getAll();

    void updateById(TcpTest test);
}
