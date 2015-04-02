package com.gamebox.dao;

import java.util.List;

import com.gamebox.model.FacebookUserBusiness;

public interface FacebookUserBusinessDao {
    
    public FacebookUserBusiness findBusinessByScopeId(String scopeId);

    public void insert(FacebookUserBusiness facebookUserBusiness);

    public FacebookUserBusiness findBusinessByScopeIdList(List<String> list);

    public void deleteByUserId(Integer userId);
    
}
