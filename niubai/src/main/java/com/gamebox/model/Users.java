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
     * 账号类型
     */
    public enum AccountType {

        /** gamebox平台账号 */
        gamebox,

        /** gamefuse平台账号 */
        gamefuse,

        /** tagame平台账号 */
        tagame,

        facebook,

        twitter,

        googlePlus,

        raidTalk
    }

    /**
     * 是否激活邮箱
     * 
     * @author zhangnan_wg
     * 
     */
    public enum ValidEmailType {
        /** 未激活 **/
        unvalided,

        /** 已激活 **/
        valided

    }

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

    private Short allowEmail;

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

    private ValidEmailType validEmail;

    private Integer gamePath;

    private Integer serverPath;

    private String media;

    private String adv;

    /**
     * 账号类型
     */
    private AccountType accountType;

    /**
     * 第三方账号是否绑定平台账号 1绑定 0未绑定
     */
    private Short accountBind;

    /**
     * 当前平台游戏点数
     */
    private Integer tokenAmount;

    /**
     * 总共获取的平台币
     */
    private Integer totalPaidToken;

    /**
     * 平台积分
     */
    private Integer pointAmount;

    /**
     * 总共获得的平台积分
     */
    private Integer totalEarnedPoint;

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

    private Boolean vipLevel;



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

    /**
     * auth_open_id
     */
    private String authOpenId;

    /**
     * 激活的邮箱，可以和账号邮箱不一致,在激活时确认
     */
    private String activatedEmail;

    /**
     * 最近玩的游戏名称
     */
    private String lastGameName;

    /**
     * 热门游戏名称，取hotgame的第一个
     */
    private String hotsGameName;

    /**
     * 游戏链接
     */
    private String gameUrl;

    /**
     * 游戏图标
     */
    private String gameLogoUrl;

    /**
     * 出生日期
     */
    private String birthday;


    /**
     * 性别：0-女，1男 这么形象你懂的
     */
    private Integer gender;

    /**
     * 返回的cookie auth
     */
    private String auth;

    @JsonProperty

    public Integer getUserId() {

        return this.userId;
    }

    public void setUserId(Integer userId) {

        this.userId = userId;
    }

    @JsonProperty
    public String getEmail() {

        return this.email.toLowerCase();
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

        // 给用户设置默认，把邮箱的@和.替换成-和_
        // if (null == this.nickname && null != email) {
        // // this.nickname = email.replace("@", "g").replace(".", "g").replace("_", "g");
        // this.nickname = "anonymous" + this.userId;
        // }
        if (null != this.nickname) {
            this.nickname = this.nickname.toLowerCase();
        }
        return this.nickname;
    }

    public void setNickname(String nickname) {

        this.nickname = nickname;
    }

    public Short getAllowEmail() {

        return this.allowEmail;
    }

    public void setAllowEmail(Short allowEmail) {

        this.allowEmail = allowEmail;
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

    public ValidEmailType getValidEmail() {

        return this.validEmail;
    }

    public void setValidEmail(ValidEmailType validEmail) {

        this.validEmail = validEmail;
    }

    public Integer getGamePath() {

        return this.gamePath;
    }

    public void setGamePath(Integer gamePath) {

        this.gamePath = gamePath;
    }

    public Integer getServerPath() {

        return this.serverPath;
    }

    public void setServerPath(Integer serverPath) {

        this.serverPath = serverPath;
    }

    public String getMedia() {

        return this.media;
    }

    public void setMedia(String media) {

        this.media = media;
    }

    public String getAdv() {

        return this.adv;
    }

    public void setAdv(String adv) {

        this.adv = adv;
    }

    public AccountType getAccountType() {

        return this.accountType;
    }

    public void setAccountType(AccountType accountType) {

        this.accountType = accountType;
    }

    public Short getAccountBind() {

        return this.accountBind;
    }

    public void setAccountBind(Short accountBind) {

        this.accountBind = accountBind;
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

    public Boolean getVipLevel() {

        return this.vipLevel;
    }

    public void setVipLevel(Boolean vipLevel) {

        this.vipLevel = vipLevel;
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

    @JsonProperty
    public Integer getTokenAmount() {

        if (null == tokenAmount) {
            return new Integer(0);
        }
        return tokenAmount;
    }

    public void setTokenAmount(Integer tokenAmount) {

        this.tokenAmount = tokenAmount;
    }

    /**
     * 获取安全密匙
     * 
     * @return 安全密匙
     */

    /**
     * 设置安全密匙
     * 
     * @param safeKey
     *            安全密匙
     */


    public Integer getTotalPaidToken() {

        if (null == totalPaidToken) {
            return new Integer(0);
        }
        return totalPaidToken;
    }

    public void setTotalPaidToken(Integer totalPaidToken) {

        this.totalPaidToken = totalPaidToken;
    }

    @JsonProperty
    public Integer getPointAmount() {

        if (null == pointAmount) {
            return new Integer(0);
        }
        return pointAmount;
    }

    public void setPointAmount(Integer pointAmount) {

        this.pointAmount = pointAmount;
    }

    public Integer getTotalEarnedPoint() {

        return totalEarnedPoint;
    }

    public void setTotalEarnedPoint(Integer totalEarnedPoint) {

        this.totalEarnedPoint = totalEarnedPoint;
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

    public String getAuthOpenId() {

        return authOpenId;
    }

    public void setAuthOpenId(String authOpenId) {

        this.authOpenId = authOpenId;
    }

    public String getActivatedEmail() {

        return activatedEmail;
    }

    public void setActivatedEmail(String activatedEmail) {

        this.activatedEmail = activatedEmail;
    }

    @JsonProperty
    public String getLastGameName() {

        return lastGameName;
    }

    public void setLastGameName(String lastGameName) {

        this.lastGameName = lastGameName;
    }

    @JsonProperty
    public String getHotsGameName() {

        return hotsGameName;
    }

    public void setHotsGameName(String hotsGameName) {

        this.hotsGameName = hotsGameName;
    }

    @JsonProperty
    public String getGameUrl() {

        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {

        this.gameUrl = gameUrl;
    }

    public String getBirthday() {

        return birthday;
    }

    public void setBirthday(String birthday) {

        this.birthday = birthday;
    }

    public Integer getGender() {

        if (null == gender) {
            gender = new Integer(1);
        }
        return gender;
    }

    public void setGender(Integer gender) {

        this.gender = gender;
    }

    @JsonProperty
    public String getAuth() {

        return auth;
    }

    public void setAuth(String auth) {

        this.auth = auth;
    }

    @JsonProperty
    public String getGameLogoUrl() {

        return gameLogoUrl;
    }

    public void setGameLogoUrl(String gameLogoUrl) {

        this.gameLogoUrl = gameLogoUrl;
    }

}