package com.gamebox.model;

/**
 * FB信息实体类
 * 
 * @author xiaoxiao
 * 
 *         2014-7-16
 */
public class FacebookAppInformation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String appId;

    private String appSecret;

    private String appToken;

    private Integer gameId;

    private String gameName;

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
     * @return the appSecret
     */
    public String getAppSecret() {

        return appSecret;
    }

    /**
     * @param appSecret
     *            the appSecret to set
     */
    public void setAppSecret(String appSecret) {

        this.appSecret = appSecret;
    }

    /**
     * @return the appToken
     */
    public String getAppToken() {

        return appToken;
    }

    /**
     * @param appToken
     *            the appToken to set
     */
    public void setAppToken(String appToken) {

        this.appToken = appToken;
    }

    /**
     * @return the gameId
     */
    public Integer getGameId() {

        return gameId;
    }

    public void setGameId(Integer gameId) {

        this.gameId = gameId;
    }

    public String getGameName() {

        return gameName;
    }

    public void setGameName(String gameName) {

        this.gameName = gameName;
    }

}
