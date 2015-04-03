package com.gamebox.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamebox.model.DirectPaymentOrder;
import com.gamebox.model.FacebookAppInformation;
import com.gamebox.model.GamePaymentTypePrice;
import com.gamebox.model.OrderStatus;
import com.gamebox.service.DirectPaymentOrderService;
import com.gamebox.service.FacebookAppInformationService;
import com.gamebox.service.GamePaymentTypePriceService;
import com.gamebox.service.ServerService;
import com.gamebox.util.DateUtils;
import com.gamebox.util.WebUtils;


@Controller
@RequestMapping("/facebook/facebookRTU")
public class RTUController {

    @Autowired
    protected DirectPaymentOrderService directPaymentOrderService;
    
    @Autowired
    protected FacebookAppInformationService facebookAppInformationService;
    
    @Autowired
    protected ServerService serverService;
    
    @Autowired
    private GamePaymentTypePriceService gamePaymentTypePriceService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String doGet(HttpServletRequest request) {
        System.out.println("/////////////////////////////facebook get");
        Enumeration<?> attributeNames = request.getParameterNames();
        
        while(attributeNames.hasMoreElements()){
            Object nextElement = attributeNames.nextElement();
            System.out.println(nextElement.toString() + ":" + request.getParameter(nextElement.toString()));
        }
        System.out.println("/////////////////////");
        String token = request.getParameter("hub.verify_token");
        if(token.equals("fbcallback")) {
            return request.getParameter("hub.challenge");//反馈OK    
        } else {
            return "error";
        }
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void doPost(HttpServletRequest request, String identifier) {
        System.out.println("/////////////////////////////facebook post");
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String res="";
            String tmp = "";
            while((tmp=in.readLine())!=null){
                //System.out.println("--------------");
                res+=tmp;
            }
            System.out.println("RTU------------------------------------");
            System.out.println(res);
            System.out.println("end------------------------------------");
            
            JSONObject rtuChargeJson  = JSONObject.fromObject(res);
            JSONArray rtuJsonArray = rtuChargeJson.getJSONArray("entry");
            JSONObject jobj  = JSONObject.fromObject(rtuJsonArray.opt(0));
            String paymentId = jobj.get("id").toString();
            JSONArray myJsonArray =  JSONArray.fromObject(jobj.get("changed_fields"));
            String act = myJsonArray.opt(0).toString();
            
            Integer timestamp = DateUtils.getCurrentTime();
            DirectPaymentOrder directPaymentOrder = directPaymentOrderService.findByTransactionId(paymentId);
            if(act.equals("disputes")){//用户提出争议
                
                if (directPaymentOrder == null) {
                    return;
                }
                directPaymentOrder.setStatus(OrderStatus.DISPUTE);
                directPaymentOrder.setUpdateTime(timestamp);
                directPaymentOrderService.update(directPaymentOrder);
                
            } else if (act.equals("actions")){
                try {
                    FacebookAppInformation facebookAppInformation = facebookAppInformationService.findByIdentifier(identifier);
                    Integer gameId = facebookAppInformation.getGameId();
                    String appAccessToken = facebookAppInformation.getAppToken();
                    if (appAccessToken == null) {
                        return;
                    }
                    String url = "https://graph.facebook.com/"+paymentId+"?access_token=" + appAccessToken;
                    
                    String resc = WebUtils.sendGet(url, null);
                    
                    JSONObject chargeJson  = JSONObject.fromObject(resc);
                    
                    JSONArray jsonArray = chargeJson.getJSONArray("actions");
                    
                    if (jsonArray.size() > 1) {
                        JSONObject action = (JSONObject)jsonArray.opt(jsonArray.size() - 1);
                        String type  = (String) action.get("type");
                        if ("refund".equals(type)) {
                            
                            if (directPaymentOrder == null) {
                                return;
                            }
                            directPaymentOrder.setStatus(OrderStatus.REFUNDED);
                            directPaymentOrder.setUpdateTime(timestamp);
                            directPaymentOrderService.update(directPaymentOrder);
                            
                            // 黑名单
                        } else if ("chargeback".equals(type) || "decline".equals(type)) {
                            
                            if (directPaymentOrder == null) {
                                return;
                            }
                            directPaymentOrder.setStatus(OrderStatus.CHARGEBACK);
                            directPaymentOrder.setUpdateTime(timestamp);
                            directPaymentOrderService.update(directPaymentOrder);
                            // 黑名单
                        }
                        return;
                    }
                    
                    String requestID = chargeJson.getString("request_id");
                    
                    String ordersn = requestID;
                    
                    JSONObject action=(JSONObject)jsonArray.opt(0);
                    //充值状态
                    String status  = (String) action.get("status");
                    //充值金额
                    BigDecimal chargeAmount  = new BigDecimal(action.getString("amount"));
                    
                    /*
                    String scopeId = null;
                    
                    String name = null;
                    try {
                        scopeId = chargeJson.getJSONObject("user").getString("id");
                        name = chargeJson.getJSONObject("user").getString("name");
                    }
                    catch (Exception e) {
                    }
                    */
                    if(status.equalsIgnoreCase("completed")) {
                     
                    //取出这个订单 准备充值
                        if (directPaymentOrder == null) {
                            
                            directPaymentOrder = directPaymentOrderService.findByOrderSn(ordersn);
                            if (directPaymentOrder.getStatus().equals(OrderStatus.COMPLETED)) {
                                return;
                            } else if (!chargeAmount.equals(directPaymentOrder.getAmount())) {
                                return;
                            }
                            Integer serverId = directPaymentOrder.getServerId();
                            //String sid = serverId.toString();
                            //Server server = serverService.findServerByGidAndSid(gameId, serverId);
                            BigDecimal amount = directPaymentOrder.getAmount();
                            String convertAmount = directPaymentOrder.getConvertAmount();
                            String roleId = directPaymentOrder.getRoleId();
                            String roleName = directPaymentOrder.getRoleName();
                            String currency = directPaymentOrder.getCurrency();
                            Integer userId = directPaymentOrder.getUserId();
                            Integer paymentTypeId = directPaymentOrder.getPaymentTypeId();
                            GamePaymentTypePrice gamePaymentTypePrice = gamePaymentTypePriceService.findByGameIdPaymentTypeIdCurrencyAndAmount(gameId, paymentTypeId, currency, amount);
                            Integer gameCoin = gamePaymentTypePrice.getGameCoin();
                            Integer coinBonus = gamePaymentTypePrice.getCoinBonus();
                            Integer gameCoins = gameCoin + coinBonus;
                            directPaymentOrder.setUpdateTime(DateUtils.getCurrentTime());
                            directPaymentOrder.setTransactionId(paymentId);
                            String description = directPaymentOrder.getDescription();
                            int result = serverService.recharge(gameId, serverId, userId, convertAmount, ordersn, roleId, roleName, gameCoins, gameCoin, coinBonus, "facebook", currency, true, description);
                            
                            if (result == 1) {
                                directPaymentOrder.setStatus(OrderStatus.COMPLETED);
                            } else if (result == 0) {
                                directPaymentOrder.setStatus(OrderStatus.GAME_FAIL);
                            }
                            directPaymentOrderService.update(directPaymentOrder);
                        }
                        
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                
            }
            
        }
        catch (Exception e) {
           e.printStackTrace();
        }
    }
}
