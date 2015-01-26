package com.gamebox.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gamebox.dao.DirectPaymentOrderDao;
import com.gamebox.model.DirectPaymentOrder;
import com.gamebox.service.DirectPaymentOrderService;

/**
 * Service - 直接支付订单
 * 
 * @author Johnny Zhu
 * @version 1.0
 */
@Service("directPaymentOrderServiceImpl")
public class DirectPaymentOrderServiceImpl implements DirectPaymentOrderService {


	@Resource(name = "directPaymentOrderDaoImpl")
	private DirectPaymentOrderDao directPaymentOrderDao;
	
	@Override
	public void save(DirectPaymentOrder directPaymentOrder) {
	    directPaymentOrderDao.save(directPaymentOrder);
	}

	@Override
	public void update(DirectPaymentOrder directPaymentOrder) {
	    directPaymentOrderDao.update(directPaymentOrder);
	}
   
    @Override
    public DirectPaymentOrder findByOrderSn(String ordersn) {

        return directPaymentOrderDao.findByOrderSn(ordersn);
    }

    @Override
    public DirectPaymentOrder findByTransactionId(String transactionId) {

        return directPaymentOrderDao.findByTransactionId(transactionId);
    }


}
