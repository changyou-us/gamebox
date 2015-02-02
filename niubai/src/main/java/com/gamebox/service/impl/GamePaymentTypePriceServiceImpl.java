package com.gamebox.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamebox.dao.GamePaymentTypePriceDao;
import com.gamebox.model.GamePaymentTypePrice;
import com.gamebox.service.GamePaymentTypePriceService;

/**
 * Service - 游戏支付金额类型
 * 
 * @author Johnny Zhu
 * @version 1.0
 */
@Service
public class GamePaymentTypePriceServiceImpl implements GamePaymentTypePriceService{


	@Autowired
	private GamePaymentTypePriceDao gamePaymentTypePriceDao;

    @Override
    public List<GamePaymentTypePrice> findByGameIdAndPaymentTypeId(Integer gameId, Integer paymentTypeId) {

        return gamePaymentTypePriceDao.findByGameIdAndPaymentTypeId(gameId, paymentTypeId);
    }

    @Override
    public GamePaymentTypePrice findByGameIdPaymentTypeIdCurrencyAndAmount(Integer gameId, Integer paymentTypeId, String currency,
            BigDecimal amount) {

        return gamePaymentTypePriceDao.findByGameIdPaymentTypeIdCurrencyAndAmount(gameId, paymentTypeId, currency, amount);
    }

    @Override
    public GamePaymentTypePrice findByGameIdPaymentTypeIdCurrencyAmountAndDescription(Integer gameId, Integer paymentTypeId,
            String currency, BigDecimal amount, String description) {
        
        return gamePaymentTypePriceDao.findByGameIdPaymentTypeIdCurrencyAmountAndDescription(gameId, paymentTypeId, currency, amount, description);
    }
    
}
