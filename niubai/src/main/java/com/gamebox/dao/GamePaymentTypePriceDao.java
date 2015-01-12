package com.gamebox.dao;

import java.math.BigDecimal;
import java.util.List;

import com.gamebox.model.GamePaymentTypePrice;



public interface GamePaymentTypePriceDao {

    List<GamePaymentTypePrice> findByGameIdAndPaymentTypeId(Integer gameId,Integer paymentTypeId);
    
    GamePaymentTypePrice findByGameIdCurrencyAndAmount(Integer gameId, Integer paymentTypeId, String currency, BigDecimal amount);
    
    GamePaymentTypePrice findByGameIdCurrencyAndAmount(Integer gameId, Integer paymentTypeId, String currency, BigDecimal amount, String description);
}
