package com.gamebox.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamebox.model.DirectPaymentOrder;
import com.gamebox.model.GamePaymentTypePrice;
import com.gamebox.model.OrderStatus;
import com.gamebox.model.Users;
import com.gamebox.service.DirectPaymentOrderService;
import com.gamebox.service.FacebookAppInformationService;
import com.gamebox.service.GamePaymentTypePriceService;
import com.gamebox.service.ServerService;
import com.gamebox.util.Base64Util;
import com.gamebox.util.DateUtils;
import com.gamebox.util.HttpUtils;
import com.gamebox.util.OrderUtils;
import com.gamebox.util.WebUtils;

@Controller
@RequestMapping("/facebook")
public class DynamicPriceController {
    
    private static final String STATUS = "status";

    private static final String OK = "1";

    private static final String NOT_LOGIN = "-1";
    
    @Autowired
    protected DirectPaymentOrderService directPaymentOrderService;

    @Autowired
    private GamePaymentTypePriceService gamePaymentTypePriceService;
    
    @Autowired
    private FacebookAppInformationService facebookAppInformationService;
    
    @Autowired
    private ServerService serverService;
    
    
    
    @RequestMapping("/issueOrder")
    @ResponseBody
    public Map<String, Object> issueOrder(HttpServletRequest request, DirectPaymentOrder directPaymentOrder) {
        Map<String, Object> map = new HashMap<String, Object>();
        Users users = (Users) request.getSession().getAttribute(WebUtils.USER);
        if (users == null) {
            map.put(STATUS, NOT_LOGIN);
            return null;
        }
        
        Integer gameId = directPaymentOrder.getGameId();
        String ordersn = OrderUtils.getOrderSn();
        GamePaymentTypePrice gamePaymentTypePrice = gamePaymentTypePriceService.findByGameIdPaymentTypeIdCurrencyAndAmount(gameId, DirectPaymentOrder.FACEBOOK_PAYMENT_TYPE_ID, directPaymentOrder.getCurrency(), directPaymentOrder.getAmount());

        Date date = new Date();
        directPaymentOrder.setPaymentTypeId(DirectPaymentOrder.FACEBOOK_PAYMENT_TYPE_ID);
        directPaymentOrder.setEmail("");
        directPaymentOrder.setUserId(users.getUserId());
        directPaymentOrder.setOrderSn(ordersn);
        directPaymentOrder.setCoinAmount(gamePaymentTypePrice.getGameCoin());
        directPaymentOrder.setConvertAmount(gamePaymentTypePrice.getConvertAmount());
        directPaymentOrder.setCreateDate(date);
        directPaymentOrder.setOrderTime(DateUtils.getCurrentTime());
        directPaymentOrder.setReissueUser("who");
        directPaymentOrder.setStatus(OrderStatus.PENDING);
        directPaymentOrder.setIp(HttpUtils.getIp(request));
        if (gameId == 8 || gameId == 6) {
            directPaymentOrder.setDescription(String.valueOf(System.currentTimeMillis()));
        }
        directPaymentOrderService.save(directPaymentOrder);
        
        map.put("ordersn", ordersn);
        map.put(STATUS, OK);
        return map;
    }
    
    @RequestMapping("/dynamicPrice")
    @ResponseBody
    public Map<String, Object> dynamicPrice(HttpServletRequest request) {
        
        System.out.println("/////////////////////////////facebook dynamicPrice");
        Enumeration<?> attributeNames = request.getParameterNames();
        
        while(attributeNames.hasMoreElements()){
            Object nextElement = attributeNames.nextElement();
            System.out.println(nextElement.toString() + ":" + request.getParameter(nextElement.toString()));
        }
        System.out.println("/////////////////////");
        
        String signed_request =request.getParameter("signed_request"); 
        
        String[] sList = signed_request.split("\\.");
        String from = "-_";
        String   to = "+/";
        String str  = sList[1];
        for( int i = 0; i < from.length(); i++ )
        {
            str = str.replace(from.charAt(i), to.charAt(i));
        }
        
        String json = Base64Util.decode(str, "UTF-8");
        
        String request_type = request.getParameter("method"); 
        
        if(request_type.equalsIgnoreCase("payments_get_item_price"))
        {

             
            //将获取的解析出来的json对象
            JSONObject requestJson  = JSONObject.fromObject(json);
            
            //String access_token  = (String) requestJson.get("oauth_token");
            //JSONObject credits = JSONObject.fromObject(requestJson.get("credits")) ;
            JSONObject payment = JSONObject.fromObject(requestJson.get("payment")) ;
            
            //过期时间
            //int expires = requestJson.getInt("expires");
            //发布时间
            int issued_at = requestJson.getInt("issued_at");
            
            //用户信息
            //String userInformation  = requestJson.getString("user");
            
            //获取用户id
            //String scopeId  = requestJson.getString("user_id");
            String ordersn = payment.getString("request_id");
            String product =  payment.getString("product");
//          获取过来的链接
            DirectPaymentOrder directPaymentOrder = directPaymentOrderService.findByOrderSn(ordersn);
            directPaymentOrder.setUpdateTime(issued_at);
            HashMap<String, Object> contents = new HashMap<String, Object>();
            
                
            contents.put("product", product);
            contents.put("amount", directPaymentOrder.getAmount().toString());
            contents.put("currency", directPaymentOrder.getCurrency());
           
            
            //准备返回跳转页面的值
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            
            jsonMap.put("content", contents);
            jsonMap.put("method", request_type);
            return jsonMap;
        }
        return null;
    }
    
    @RequestMapping("/product/{ordersn}")
    public String product(ModelMap model, @PathVariable Integer gameId, @PathVariable String ordersn) {
        
        System.out.println(ordersn);
        model.addAttribute("", "");
        
        return "/webgame/facebook/product";
    }
    
}
