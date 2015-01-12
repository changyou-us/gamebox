package com.gamebox.dao;

import java.util.List;

public interface BaseDao <T> {

    int insert(T pojo);
    
    int update(T pojo);
    
    List<T> selectAll();
}
