package com.gamebox.model;

import java.math.BigDecimal;

/**
 * GameboxGamePaymentTypePrice entity. @author MyEclipse Persistence Tools
 */
public class GamePaymentTypePrice extends BaseEntity implements java.io.Serializable {

	// Fields
	
	/** 游戏ID */
	private Integer gameId;
	/** 支付方式/渠道ID */
	private Integer paymentTypeId;
	/** 订单金额 */
	private BigDecimal amount;
	/** 订单金额的货币种类缩写，如USD */
	private String currency;
	/** 实际可兑换金额 */
	private String convertAmount;
	/** 游戏币数额 */
	private Integer gameCoin;
	/** 游戏币奖励 */
	private Integer coinBonus;
	/** 游戏币名称 */
	private String coinName;
	/** 说明 */
	private String description;


	public Integer getGameId() {
		return this.gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public Integer getPaymentTypeId() {
		return this.paymentTypeId;
	}

	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getConvertAmount() {
		return this.convertAmount;
	}

	public void setConvertAmount(String convertAmount) {
		this.convertAmount = convertAmount;
	}

	public Integer getGameCoin() {
		return this.gameCoin;
	}

	public void setGameCoin(Integer gameCoin) {
		this.gameCoin = gameCoin;
	}

	public Integer getCoinBonus() {
		return this.coinBonus;
	}

	public void setCoinBonus(Integer coinBonus) {
		this.coinBonus = coinBonus;
	}

	public String getCoinName() {
		return this.coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}