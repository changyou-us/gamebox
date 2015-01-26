package com.gamebox.dao;

import com.gamebox.model.DirectPaymentOrder;


public interface DirectPaymentOrderDao {

    void save(DirectPaymentOrder directPaymentOrder);

    void update(DirectPaymentOrder directPaymentOrder);

    DirectPaymentOrder findByOrderSn(String ordersn);

    DirectPaymentOrder findByTransactionId(String transactionId);

}
