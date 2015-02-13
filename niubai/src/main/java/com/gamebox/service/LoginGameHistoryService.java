/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV
 * Project:new_gamebox_trunk Package:com.gamebox.service File:LoginGameHistoryService.java Date:2014年8月4日
 */
package com.gamebox.service;

import com.gamebox.model.LoginGameHistory;

/**
 * Service - 管理员
 * 
 * @author Dick niu
 * @version 1.0
 */
public interface LoginGameHistoryService {
    
    public void save(LoginGameHistory loginGameHistory);
    
    public int findByUserIdAndGameId(Integer userId, Integer gameId);
}
