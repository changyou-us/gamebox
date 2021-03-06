/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV
 * Project:new_gamebox_trunk Package:com.gamebox.dao File:LoginGameHistoryDao.java Date:2014年8月4日
 */
package com.gamebox.dao;

import org.apache.ibatis.annotations.Param;

import com.gamebox.model.LoginGameHistory;

/**
 * Dao - 管理员
 * 
 * @author Dick Niu
 * @version 1.0
 */
public interface LoginGameHistoryDao {

    public void insert(LoginGameHistory loginGameHistory);

    public int findByUserIdAndGameId(@Param("userId") Integer userId, @Param("gameId") Integer gameId);
}
