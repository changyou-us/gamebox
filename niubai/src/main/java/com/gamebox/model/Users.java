package com.gamebox.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GameboxUsers entity. @author MyEclipse Persistence Tools
 */
@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, creatorVisibility = Visibility.NONE)

public class Users implements java.io.Serializable {

    // Fields

    /**
     * 序列ID
     */
    private static final long serialVersionUID = 4285394657139766045L;

    /**
     * ID，自动生成
     */
    private Integer userId;

    /**
     * 邮箱，这里作为用户的登陆名和主键
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 密匙，gamebox平台用
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickname;

    private Integer regTime;

    private String regIp;

    /**
     * 登陆国家
     */
    private String regCountry;

    /**
     * 最近登录时间
     */
    private Integer lastLogin;

    /**
     * 最近登录IP
     */
    private String lastIp;

    /**
     * 账号类型
     */
    private AccountType accountType;

    /**
     * 登陆次数
     */
    private Integer loginNum;

    /**
     * 是否黑名单，true-是黑名单，false-不是黑没名单
     */
    private Boolean blacklist;

    /**
     * 是否禁止充值 true-禁止充值 false-不禁止充值
     */
    private AuthType payAuth;

    /**
     * gamebox_uid
     */
    private Integer gameboxUid;

    /**
     * gamefuse_uid
     */
    private Integer gamefuseUid;

    /**
     * tagame_uid
     */
    private Integer tagameUid;

    @JsonProperty

    public Integer getUserId() {

        return this.userId;
    }

    public void setUserId(Integer userId) {

        this.userId = userId;
    }

    @JsonProperty
    public String getEmail() {

        return this.email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return this.password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getSalt() {

        return this.salt;
    }

    public void setSalt(String salt) {

        this.salt = salt;
    }

    @JsonProperty
    public String getNickname() {

        if (null != this.nickname) {
            this.nickname = this.nickname.toLowerCase();
        }
        return this.nickname;
    }

    public void setNickname(String nickname) {

        this.nickname = nickname;
    }

    public Integer getRegTime() {

        return this.regTime;
    }

    public void setRegTime(Integer regTime) {

        this.regTime = regTime;
    }

    public String getRegIp() {

        return this.regIp;
    }

    public void setRegIp(String regIp) {

        this.regIp = regIp;
    }

    public Integer getLastLogin() {

        return this.lastLogin;
    }

    public void setLastLogin(Integer lastLogin) {

        this.lastLogin = lastLogin;
    }

    public String getLastIp() {

        return this.lastIp;
    }

    public void setLastIp(String lastIp) {

        this.lastIp = lastIp;
    }

    public AccountType getAccountType() {

        return this.accountType;
    }

    public void setAccountType(AccountType accountType) {

        this.accountType = accountType;
    }

    public Boolean getBlacklist() {

        if (null == this.blacklist) {
            return new Boolean(false);
        }
        return this.blacklist;
    }

    public void setBlacklist(Boolean blacklist) {

        this.blacklist = blacklist;
    }

    public AuthType getPayAuth() {

        return this.payAuth;
    }

    public void setPayAuth(AuthType payAuth) {
        
        if (payAuth == null) {
            this.payAuth = AuthType.PERMIT;
        } else {
            this.payAuth = payAuth;
        }
        
    }

    public String getRegCountry() {

        return regCountry;
    }

    public void setRegCountry(String regCountry) {

        this.regCountry = regCountry;
    }

    public Integer getLoginNum() {

        if (null == loginNum) {
            return new Integer(0);
        }
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {

        this.loginNum = loginNum;
    }

    public Integer getGameboxUid() {

        return gameboxUid;
    }

    public void setGameboxUid(Integer gameboxUid) {

        this.gameboxUid = gameboxUid;
    }

    public Integer getGamefuseUid() {

        return gamefuseUid;
    }

    public void setGamefuseUid(Integer gamefuseUid) {

        this.gamefuseUid = gamefuseUid;
    }

    public Integer getTagameUid() {

        return tagameUid;
    }

    public void setTagameUid(Integer tagameUid) {

        this.tagameUid = tagameUid;
    }


}