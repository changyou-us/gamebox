package com.gamebox.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gamebox.model.GamePaymentTypePrice;



public interface GamePaymentTypePriceDao {
    
    List<GamePaymentTypePrice> findByGameIdAndPaymentTypeId(@Param("gameId")Integer gameId, @Param("paymentTypeId")Integer paymentTypeId);

    GamePaymentTypePrice findByGameIdPaymentTypeIdCurrencyAndAmount(@Param("gameId")Integer gameId, @Param("paymentTypeId")Integer paymentTypeId, @Param("currency")String currency, @Param("amount")BigDecimal amount);
    
    GamePaymentTypePrice findByGameIdPaymentTypeIdCurrencyAmountAndDescription(@Param("gameId")Integer gameId, @Param("paymentTypeId")Integer paymentTypeId, @Param("currency")String currency, @Param("amount")BigDecimal amount, @Param("description")String description);

}
