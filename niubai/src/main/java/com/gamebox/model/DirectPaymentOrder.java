package com.gamebox.model;

import java.math.BigDecimal;


/**
 * GameboxGameDirectPayOrder entity. @author MyEclipse Persistence Tools
 */
public class DirectPaymentOrder extends BaseEntity implements java.io.Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    // Fields
	/**订单ID，订单表的业务主键，值为UUID类型  */
	private String orderSn;
	/** 订单金额 */
	private BigDecimal amount;
	/** 实际可兑换金额 */
	private String convertAmount;
	/** 订单金额的货币种类缩写，如USD */
	private String currency;
	/** 下订单的时间 */
	private Integer orderTime;
	/** 订单最后修改时间 */
	private Integer updateTime;
	/** 下订单的用户IP */
	private String ip;
	/** 下订单的用户名 */
	private String email;
	/** 用户ID，外键，参考用户表的userId字段，值为UUID类型 */
	private Integer userId;
	/** 该订单购买的点数*/
	private Integer coinAmount;
	/** 支付方式/渠道ID*/
	private Integer paymentTypeId;
	/** 单订支付状态，0为未支付，1为支付成功，-1为支付失败，2取消充值，3订单已退款，4订单已经过期*/
	private OrderStatus status;
	/** 补单操作管理员用户名*/
	private String reissueUser;
	/** 说明*/
	private String description;
	/** 角色名*/
	private String roleId;
	
	private String roleName;
	/** 游戏id*/
	private Integer gameId;
	/** 服务器id*/
	private Integer serverId;
	/** 是否是订阅0**/
	private Integer subscribe;
	
	private String transactionId;
	// Property accessors

	
	private String gameName;
	private String serverName;
	
	public String getOrderSn() {
		return this.orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getConvertAmount() {
		return this.convertAmount;
	}

	public void setConvertAmount(String convertAmount) {
		this.convertAmount = convertAmount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Integer orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCoinAmount() {
		return this.coinAmount;
	}

	public void setCoinAmount(Integer coinAmount) {
		this.coinAmount = coinAmount;
	}

	public Integer getPaymentTypeId() {
		return this.paymentTypeId;
	}

	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public OrderStatus getStatus() {
		return this.status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getReissueUser() {
		return this.reissueUser;
	}

	public void setReissueUser(String reissueUser) {
		this.reissueUser = reissueUser;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    /**
     * @param gameId the gameId to set
     */
    public void setGameId(Integer gameId) {

        this.gameId = gameId;
    }

    /**
     * @return the gameId
     */
    public Integer getGameId() {

        return gameId;
    }

    /**
     * @param serverId the serverId to set
     */
    public void setServerId(Integer serverId) {

        this.serverId = serverId;
    }

    /**
     * @return the serverId
     */
    public Integer getServerId() {

        return serverId;
    }

    public String getGameName() {
    
        return gameName;
    }

    
    public void setGameName(String gameName) {
    
        this.gameName = gameName;
    }

    public String getServerName() {
    
        return serverName;
    }

    
    public void setServerName(String serverName) {
    
        this.serverName = serverName;
    }
    
    /**
     * @param subscribe the subscribe to set
     */
    public void setSubscribe(Integer subscribe) {

        this.subscribe = subscribe;
    }

    /**
     * @return the subscribe
     */
    public Integer getSubscribe() {

        return subscribe;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(String roleId) {

        this.roleId = roleId;
    }

    /**
     * @return the roleId
     */
    public String getRoleId() {

        return roleId;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {

        this.roleName = roleName;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {

        return roleName;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(String transactionId) {

        this.transactionId = transactionId;
    }

    /**
     * @return the transactionId
     */
    public String getTransactionId() {

        return transactionId;
    }

    

}