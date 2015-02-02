package com.gamebox.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * GameboxServer entity. @author MyEclipse Persistence Tools
 */
public class Server extends BaseEntity implements java.io.Serializable {

    /**
     * 序列流水号
     */
    private static final long serialVersionUID = 1115035139702033159L;

    /** 静态路径 */
    //private static String staticPath;

    /**
     * 服务id，服务器唯一标识
     */
    private Integer serverId;

    /**
     * 游戏ID，唯一标识
     */
    private Integer gameId;

    /**
     * 游戏名称
     */
    private String gameName;
    
    /**
     * 服务器名称
     */
    private String name;

    /**
     * 服务器显示ID
     */
    private Integer showId;

    /**
     * 是否显示 0显示 1不显示
     */
    private DisplayType display;

    /**
     * 描述
     */
    private String description;

    /**
     * 登陆url
     */
    @JsonIgnore
    private String loginUrl;

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 充值url
     */
    @JsonIgnore
    private String rechargeUrl;

    /**
     * 角色查询url，没有角色则不填
     */
    @JsonIgnore
    private String roleUrl;

    /**
     * 工作状态：0空闲 1良好 2繁忙 3维护
     */
    private StatusType status;

    /**
     * 在线统计url
     */
    @JsonIgnore
    private String statisticUrl;

    /**
     * 其它私钥（登陆，在线统计等）
     */
    @JsonIgnore
    private String publicKey;

    /**
     * 充值私钥
     */
    @JsonIgnore
    private String rechargeKey;

    /**
     * 查询角色私钥
     */
    @JsonIgnore
    private String roleKey;

    /**
     * 其它私钥（登陆，在线统计等）
     */
    @JsonIgnore
    private String loginSecret;

    /**
     * 充值私钥
     */
    @JsonIgnore
    private String rechargeSecret;

    /**
     * 查询角色私钥
     */
    @JsonIgnore
    private String roleSecret;

    /**
     * 充值成功字符串
     */
    @JsonIgnore
    private String rechargeSign;

    /**
     * 服务器设置的时区
     */
    private TimezoneType timezone;

    /**
     * 游戏服务器账号查询，查看玩家在某服务器中游戏过
     */
    @JsonIgnore
    private String playerInfoUrl;

    /**
     * 是否联运方的单服
     */
    private Partner partner;

    /**
     * 其他参数,标签格式
     */
    @JsonIgnore
    private String otherParam;

    /**
     * 是否新服,0否 1是
     */
    private IsNewType isNew;

    /**
     * 是否推荐 0一般，1推荐
     */
    private RecommendedType recommended;

    /**
     * 合服后ID
     */
    @JsonIgnore
    private Integer transferedId;

    /**
     * 合服后ID是否用于登录充值
     */
    private TransIdUsedStatus transIdUsedStatus;

    /**
     * 排行榜地址
     */
    @JsonIgnore
    private String rankUrl;

    /**
     * 排行榜加密规则
     */
    @JsonIgnore
    private String rankSecret;

    /**
     * 排行榜秘钥
     */
    @JsonIgnore
    private String rankKey;

    public Integer getServerId() {

        return this.serverId;
    }

    public void setServerId(Integer serverId) {

        this.serverId = serverId;
    }

    public Integer getGameId() {

        return this.gameId;
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

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Integer getShowId() {

        return this.showId;
    }

    public void setShowId(Integer showId) {

        this.showId = showId;
    }

    public DisplayType getDisplay() {

        return this.display;
    }

    public void setDisplay(DisplayType display) {

        this.display = display;
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getLoginUrl() {

        return this.loginUrl;
    }

    public void setLoginUrl(String loginUrl) {

        this.loginUrl = loginUrl;
    }

    public Integer getOrders() {

        return this.orders;
    }

    public void setOrders(Integer orders) {

        this.orders = orders;
    }

    public String getRechargeUrl() {

        return this.rechargeUrl;
    }

    public void setRechargeUrl(String rechargeUrl) {

        this.rechargeUrl = rechargeUrl;
    }

    public String getRoleUrl() {

        return this.roleUrl;
    }

    public void setRoleUrl(String roleUrl) {

        this.roleUrl = roleUrl;
    }

    public StatusType getStatus() {

        return this.status;
    }

    public void setStatus(StatusType status) {

        this.status = status;
    }

    public String getStatisticUrl() {

        return this.statisticUrl;
    }

    public void setStatisticUrl(String statisticUrl) {

        this.statisticUrl = statisticUrl;
    }

    public String getPublicKey() {

        return this.publicKey;
    }

    public void setPublicKey(String publicKey) {

        this.publicKey = publicKey;
    }

    public String getRechargeKey() {

        return this.rechargeKey;
    }

    public void setRechargeKey(String rechargeKey) {

        this.rechargeKey = rechargeKey;
    }

    public String getRoleKey() {

        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        if (roleKey == null) {
            this.roleKey = "";
        } else {
            this.roleKey = roleKey;
        }
        
    }

    public String getLoginSecret() {

        return loginSecret;
    }

    public void setLoginSecret(String loginSecret) {

        this.loginSecret = loginSecret;
    }

    public String getRechargeSecret() {

        return rechargeSecret;
    }

    public void setRechargeSecret(String rechargeSecret) {

        this.rechargeSecret = rechargeSecret;
    }

    public String getRoleSecret() {

        return roleSecret;
    }

    public void setRoleSecret(String roleSecret) {
        if (roleSecret == null) {
            this.roleSecret = "";
        } else {
            this.roleSecret = roleSecret;
        }
        
    }

    public String getRechargeSign() {

        return rechargeSign;
    }

    public void setRechargeSign(String rechargeSign) {

        this.rechargeSign = rechargeSign;
    }

    public TimezoneType getTimezone() {

        return this.timezone;
    }

    public void setTimezone(TimezoneType timezone) {

        this.timezone = timezone;
    }

    public String getPlayerInfoUrl() {

        return this.playerInfoUrl;
    }

    public void setPlayerInfoUrl(String playerInfoUrl) {

        this.playerInfoUrl = playerInfoUrl;
    }

    public Partner getPartner() {

        return this.partner;
    }

    public void setPartner(Partner partner) {

        this.partner = partner;
    }

    public String getOtherParam() {

        return this.otherParam;
    }

    public void setOtherParam(String otherParam) {

        this.otherParam = otherParam;
    }

    public IsNewType getIsNew() {

        return this.isNew;
    }

    public void setIsNew(IsNewType isNew) {

        this.isNew = isNew;
    }

    public RecommendedType getRecommended() {

        return this.recommended;
    }

    public void setRecommended(RecommendedType recommended) {

        this.recommended = recommended;
    }

    public Integer getTransferedId() {

        return this.transferedId;
    }

    public TransIdUsedStatus getTransIdUsedStatus() {

        return transIdUsedStatus;
    }

    public void setTransIdUsedStatus(TransIdUsedStatus transIdUsedStatus) {

        if (transIdUsedStatus == null) {
            this.transIdUsedStatus = TransIdUsedStatus.NOT_USED;
        } else {
            this.transIdUsedStatus = transIdUsedStatus;
        }
    }

    public void setTransferedId(Integer transferedId) {

        this.transferedId = transferedId;
    }

    public String getRankUrl() {

        return rankUrl;
    }

    public void setRankUrl(String rankUrl) {

        this.rankUrl = rankUrl;
    }

    public String getRankSecret() {

        return rankSecret;
    }

    public void setRankSecret(String rankSecret) {

        this.rankSecret = rankSecret;
    }

    public String getRankKey() {

        return rankKey;
    }

    public void setRankKey(String rankKey) {

        this.rankKey = rankKey;
    }


}