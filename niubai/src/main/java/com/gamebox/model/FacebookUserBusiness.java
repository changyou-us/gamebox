package com.gamebox.model;

import java.io.Serializable;

/**
 * FB Business实体类
 * 
 * @author xiaoxiao
 * 
 *         2014-7-16
 */
public class FacebookUserBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appId;

    private String scopeId;

    private String user_id;

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

    /**
     * @param user_id
     *            the user_id to set
     */
    public void setUser_id(String user_id) {

        this.user_id = user_id;
    }

    /**
     * @return the user_id
     */
    public String getUser_id() {

        return user_id;
    }

}
