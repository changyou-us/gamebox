package com.gamebox.model;

import java.io.Serializable;

/**
 * FB Business实体类
 * 
 * @author xiaoxiao
 * 
 *         2014-7-16
 */
public class FacebookUserBusiness extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appId;

    private String scopeId;

    private String userId;

    /**
     * @return the appId
     */
    public String getAppId() {

        return appId;
    }

    /**
     * @param appId
     *            the appId to set
     */
    public void setAppId(String appId) {

        this.appId = appId;
    }

    /**
     * @param scopeId
     *            the scopeId to set
     */
    public void setScopeId(String scopeId) {

        this.scopeId = scopeId;
    }

    /**
     * @return the scopeId
     */
    public String getScopeId() {

        return scopeId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getUserId() {

        return userId;
    }

}
