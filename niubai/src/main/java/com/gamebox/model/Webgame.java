/*
 * Copyright 2013-2014 gamebox. All rights reserved.
 * Support: http://www.gamebox.com
 * Team: WG DEV
 * Project:niubai
 * Package:com.gamebox.model
 * File:Webgame.java
 * Date:2014年12月29日
 */
package com.gamebox.model;

/**
 * @author zhangnan_wg
 * @version 
 * @since 2014年12月29日
 */
public class Webgame extends BaseEntity implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8995280889705245357L;

    private Integer gameId;

    private String name;

    private String identifier;

    private String description;

    private OpenStatusType openStatus;

    private String hight;

    private String width;

    private String homepage;

    private ActivationCodeType activationCode;

    private ActivationGrantType activationGrant;

    private RechargeStatusType rechargeStatus;

    private String defaultServerId;

    private String logoUrl;

    private String coinName;

    private String area;

    private GameTypeType gameType;

    private GameOwnerType gameOwner;

    private String rankUrl;

    private String rankSecret;

    private String rankKey;

    public Integer getGameId() {

        return this.gameId;
    }

    public void setGameId(Integer gameId) {

        this.gameId = gameId;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getIdentifier() {

        return this.identifier;
    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public OpenStatusType getOpenStatus() {

        return this.openStatus;
    }

    public void setOpenStatus(OpenStatusType openStatus) {

        this.openStatus = openStatus;
    }

    public String getHight() {

        return this.hight;
    }

    public void setHight(String hight) {

        this.hight = hight;
    }

    public String getWidth() {

        return this.width;
    }

    public void setWidth(String width) {

        this.width = width;
    }

    public String getHomepage() {

        return this.homepage;
    }

    public void setHomepage(String homepage) {

        this.homepage = homepage;
    }

    public ActivationCodeType getActivationCode() {

        return this.activationCode;
    }

    public void setActivationCode(ActivationCodeType activationCode) {

        this.activationCode = activationCode;
    }

    public ActivationGrantType getActivationGrant() {

        return this.activationGrant;
    }

    public void setActivationGrant(ActivationGrantType activationGrant) {

        this.activationGrant = activationGrant;
    }

    public RechargeStatusType getRechargeStatus() {

        return this.rechargeStatus;
    }

    public void setRechargeStatus(RechargeStatusType rechargeStatus) {

        this.rechargeStatus = rechargeStatus;
    }

    public String getDefaultServerId() {

        return this.defaultServerId;
    }

    public void setDefaultServerId(String defaultServerId) {

        this.defaultServerId = defaultServerId;
    }

    public String getLogoUrl() {

        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl) {

        this.logoUrl = logoUrl;
    }

    public String getCoinName() {

        return this.coinName;
    }

    public void setCoinName(String coinName) {

        this.coinName = coinName;
    }

    public String getArea() {

        return this.area;
    }

    public void setArea(String area) {

        this.area = area;
    }

    public GameOwnerType getGameOwner() {

        return this.gameOwner;
    }

    public void setGameOwner(GameOwnerType gameOwner) {

        this.gameOwner = gameOwner;
    }

    public GameTypeType getGameType() {

        return this.gameType;
    }

    public void setGameType(GameTypeType gameType) {

        this.gameType = gameType;
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

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((gameId == null) ? 0 : gameId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Webgame other = (Webgame) obj;
        if (gameId == null) {
            if (other.gameId != null)
                return false;
        }
        else if (!gameId.equals(other.gameId))
            return false;
        return true;
    }
}
