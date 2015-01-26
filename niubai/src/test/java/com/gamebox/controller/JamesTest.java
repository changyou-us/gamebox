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

    //@Autowired  
    private WebApplicationContext wac;  
  
    private MockMvc mockMvc;  
  
    @Before  
    public void setup() {  
        // webAppContextSetup 注意上面的static import  
        // webAppContextSetup 构造的WEB容器可以添加fileter 但是不能添加listenCLASS  
        // WebApplicationContext context =  
        // ContextLoader.getCurrentWebApplicationContext();  
        // 如果控制器包含如上方法 则会报空指针  
        this.mockMvc = webAppContextSetup(this.wac).build();  
    }  
    
    @Test  
    public void test() throws Exception {  
        mockMvc.perform(get("/test/save"));  
    } 
    
    @Test  
    public void game() throws Exception {  
        
        String Uname = "1";
        String userid = "1";
        String GameId = "";
        String ServerId = "s0";
        String Key = "MZfMA0GCSqGSIb3DQEBAQUAA3FSFOPSQKBgQDDMz3OCs5esvsp";
        String Time = DateUtils.getCurrentTime().toString();
        String from = "facebook";
        String siteurl = "";
        
        String Sign = DigestUtils.md5Hex("Uname=$" + Uname + "&userid=$" + userid + "&GameId=$" + GameId + "&ServerId=$" + ServerId + "&Key=$" + Key + "&Time=$" + Time + "&al=$1&from=$facebook&siteurl=$");
        String url = "http://mf-test.gamebox.com/mzhome/enlogin?Uname=" + Uname + "&userid=" + userid + "&GameId=" + GameId + "&ServerId=" + ServerId + "&Time=" + Time + "&al=1&from=facebook&siteurl=&Sign=" + Sign; 
        System.out.println(url);
    } 
    
    @Test
    public void made() {
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
