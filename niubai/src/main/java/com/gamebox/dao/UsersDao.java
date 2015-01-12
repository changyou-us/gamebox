/*
 * Copyright 2013-2014 gamebox. All rights reserved. Support: http://www.gamebox.com Team: WG DEV Project:niubai
 * Package:com.gamebox.dao File:TcpTestMapper.java Date:2014年12月15日
 */
package com.gamebox.dao;

import java.util.List;

import com.gamebox.model.Users;

/**
 * @author wuji
 * 
 */
public interface UsersDao extends BaseDao<Users>{

    Users findUserById(String uid);
}
