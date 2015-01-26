package com.gamebox.service;

import java.math.BigDecimal;
import java.util.List;

import com.gamebox.model.GamePaymentTypePrice;

/**
 * Service - 游戏支付金额类型
 * 
 * @author Johnny Zhu
 * @version 1.0
 */
public interface GamePaymentTypePriceService {

    List<GamePaymentTypePrice> findByGameIdAndPaymentTypeId(Integer gameId, Integer paymentTypeId);

    GamePaymentTypePrice findByGameIdPaymentTypeIdCurrencyAndAmount(Integer gameId, Integer paymentTypeId, String currency, BigDecimal amount);

    GamePaymentTypePrice findByGameIdPaymentTypeIdCurrencyAmountAndDescription(Integer gameId, Integer paymentTypeId, String currency, BigDecimal amount, String description);
}
