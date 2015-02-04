package com.gamebox.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.gamebox.dao.GamePaymentTypePriceDao;
import com.gamebox.model.GamePaymentTypePrice;
import com.gamebox.util.DateUtils;
import com.mybatis.config.DbHelper;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;  
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-mvc.xml", "classpath:applicationContext-mybatis.xml"})
@WebAppConfiguration
public class JamesTest {

    @Autowired  
    private WebApplicationContext wac;  
  
    private MockMvc mockMvc;  
  
    @Before  
    public void setup() {  
        // webAppContextSetup 注意上面的static import  
        // webAppContextSetup 构造的WEB容器可以添加fileter 但是不能添加listenCLASS  
         WebApplicationContext context =  
         ContextLoader.getCurrentWebApplicationContext();  
        // 如果控制器包含如上方法 则会报空指针  
        this.mockMvc = webAppContextSetup(this.wac).build();  
    }  
      
    public void test() throws Exception {  
        mockMvc.perform(get("/test/save"));  
    } 
    
    public void game() throws Exception {  
        
        String Uname = "5037";
        String GameId = "20";
        String ServerId = "S0";
        String money = "100";
        String RoleId = "1000006";
        String ordersn = "01901b1dac684e4e9709a094420fa7dc";
        String key = "MZfMA0GCSqGSIb3DQEBAQUAA3FSFOPSQKBgQDDMz3OCs5esvsp";
        String a = "Depay=$&gDepay=$&addcoin=$&Uname=$" + Uname + "&Money=$" + money + "&GameId=$" + GameId + "&ServiceId=$" + ServerId + "&RoleId=$" + RoleId + "&Transactionid=$" + ordersn + "&Key=$" + key;
        System.out.println(a);
        System.out.println("Depay=$&gDepay=$&addcoin=$&Uname= $5037&Money=$100&GameId=$20&ServiceId=$S0&RoleId=$1000006&Transactionid=$01901b1dac684e4e9709a094420fa7dc&Key=$MZfMA0GCSqGSIb3DQEBAQUAA3FSFOPSQKBgQDDMz3OCs5esvsp");
        String sign = DigestUtils.md5Hex(a);
        String url = "http://mf-test.gamebox.com/mzpay/enpay?addcoin=&Uname=" + Uname + "&GameId=" + GameId+ "&Money=" + money + "&ServiceId=" + ServerId + "&RoleId=" + RoleId + "&Transactionid=" + ordersn + "&Depay=&gDepay=&Sign=" + sign; 
        System.out.println(url);
        System.out.println("http://mf-test.gamebox.com/mzpay/enpay?addcoin=&Uname=5037&GameId=20&Money=100&ServiceId=S0&RoleId=1000006&Transactionid=01901b1dac684e4e9709a094420fa7dc&Depay=&gDepay=&Sign=214a12c99bee496ac3d3847fa28ea0a9");
    } 
    
    @Test
    public void made() {
        try {
            mockMvc.perform(get("/test/ksd"));
            //mockMvc.perform(get("/test/evict"));
            mockMvc.perform(get("/test/ksd"));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        /*
        SqlSession session = DbHelper.getInstance().getSqlSession();
        GamePaymentTypePriceDao gamePaymentTypePriceService = session.getMapper(GamePaymentTypePriceDao.class);
        
        List<GamePaymentTypePrice> select = gamePaymentTypePriceService.select();
        System.out.println(select.size());
        Integer gameId = 14;
        Integer paymentTypeId = 1;
        List<GamePaymentTypePrice> findByGameIdAndPaymentTypeId = gamePaymentTypePriceService.findByGameIdAndPaymentTypeId(gameId, paymentTypeId);
        System.out.println(findByGameIdAndPaymentTypeId.size());
        System.out.println(findByGameIdAndPaymentTypeId.get(0).getCoinName());
        
        
        String currency = "USD";
        BigDecimal amount = new BigDecimal(5);
        GamePaymentTypePrice findByGameIdCurrencyAndAmount = gamePaymentTypePriceService.findByGameIdCurrencyAndAmount(gameId, paymentTypeId, currency, amount);
        String description = "abc";
        System.out.println(findByGameIdCurrencyAndAmount.getAmount());
        
        GamePaymentTypePrice findByGameIdCurrencyAndAmount2 = gamePaymentTypePriceService.findByGameIdCurrencyAndAmount(gameId, paymentTypeId, currency, amount, description);
        System.out.println(findByGameIdCurrencyAndAmount2.getAmount());
        
        session.commit();
        session.close();
        */
    }
}
