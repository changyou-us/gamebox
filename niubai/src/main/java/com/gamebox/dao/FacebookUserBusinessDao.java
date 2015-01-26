package com.gamebox.dao;

import com.gamebox.model.FacebookUserBusiness;

public interface FacebookUserBusinessDao {
    
    public FacebookUserBusiness findBusinessByScopeId(String scopeId);

    public void insert(FacebookUserBusiness facebookUserBusiness);
    
}
