package com.gamebox.model;

public class LoginGameHistory extends BaseEntity {

    /**
     * 序列ID
     */
    private static final long serialVersionUID = -1189110860846967620L;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 游戏ID
     */
    private Integer gameId;

    /**
     * 服务id
     */
    private Integer serverId;

    /**
     * 登录次数
     */
    private Integer loginTime;
    
    private String userIp;

    public Integer getUserId() {

        return userId;
    }

    public void setUserId(Integer userId) {

        this.userId = userId;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public Integer getGameId() {

        return gameId;
    }

    public void setGameId(Integer gameId) {

        this.gameId = gameId;
    }

    public Integer getServerId() {

        return serverId;
    }

    public void setServerId(Integer serverId) {

        this.serverId = serverId;
    }

    public Integer getLoginTime() {

        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {

        this.loginTime = loginTime;
    }

    /**
     * @param userIp the userIp to set
     */
    public void setUserIp(String userIp) {

        this.userIp = userIp;
    }

    /**
     * @return the userIp
     */
    public String getUserIp() {

        return userIp;
    }

}
