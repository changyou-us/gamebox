package com.gamebox.service;

import com.gamebox.model.DirectPaymentOrder;

/**
 * Service - 直接支付订单
 * 
 * @author Johnny Zhu
 * @version 1.0
 */
public interface DirectPaymentOrderService {

    DirectPaymentOrder findByOrderSn(String orderSn);

    DirectPaymentOrder findByTransactionId(String transactionId);

    void save(DirectPaymentOrder directPaymentOrder);

    void update(DirectPaymentOrder directPaymentOrder);
}
