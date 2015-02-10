package com.gamebox.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamebox.model.TcpTest;
import com.gamebox.service.GamePaymentTypePriceService;
import com.gamebox.service.TcpTestService;
import com.gamebox.service.UsersService;
import com.gamebox.xmemcached.MemcachedCacheManager;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private MemcachedCacheManager memcachedCacheManager;
    
    @Autowired
    private TcpTestService testService;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private GamePaymentTypePriceService gamePaymentTypePriceService;

    @RequestMapping(value = "/baidu", method = RequestMethod.GET)
    public String baidu(HttpServletRequest request, ModelMap model) {
        
        //TEST1
        return "/auth_fb";
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<TcpTest> getAll(HttpServletRequest request, ModelMap model) {

        System.out.println(123456);
        //List<TcpTest> list = testService.getAll();
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public String update(TcpTest test) {

        testService.updateById(test);
        return "OK";
    }
    
    @RequestMapping(value = "/ksd", method = RequestMethod.GET)
    @ResponseBody
    public String ksd() {
        return "OK";
    }
    
    @RequestMapping(value = "/evict", method = RequestMethod.GET)
    @ResponseBody
    public String evict() {

        String[] arrays = {"FacebookAppInformationFindByGameId","FacebookAppInformationFindByGameName","server"};
        
        for (String s : arrays) {
            memcachedCacheManager.getCache(s).clear();
        }
        return "OK";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public int save(HttpServletRequest request, ModelMap model) {
        testService.test();
        /*
        Integer gameId = 14;
        Integer paymentTypeId = 1;
        List<GamePaymentTypePrice> findByGameIdAndPaymentTypeId = gamePaymentTypePriceService.findByGameIdAndPaymentTypeId(gameId, paymentTypeId);
        System.out.println(findByGameIdAndPaymentTypeId.size());
        String currency = "USD";
        BigDecimal amount = new BigDecimal(5);
        GamePaymentTypePrice findByGameIdCurrencyAndAmount = gamePaymentTypePriceService.findByGameIdPaymentTypeIdCurrencyAndAmount(gameId, paymentTypeId, currency, amount);
        String description = "abc";
        System.out.println(findByGameIdCurrencyAndAmount.getAmount());
        GamePaymentTypePrice findByGameIdCurrencyAndAmount2 = gamePaymentTypePriceService.findByGameIdPaymentTypeIdCurrencyAmountAndDescription(gameId, paymentTypeId, currency, amount, description);
        System.out.println(findByGameIdCurrencyAndAmount2.getAmount());

        Users users = new Users();
        String email = OrderUtils.getOrderSn();
        System.out.println(email);
        users.setEmail(email);
        usersService.save(users);
        
        Users find1 = usersService.findUserByUserId(users.getUserId().toString());
        System.out.println(find1.getEmail());
        Users find2 = usersService.findUserByEmail(users.getEmail());
        System.out.println(find1 == find2);
        System.out.println(find2.getUserId());
        
        
        Integer gameId = 14;
        Integer paymentTypeId = 1;
        List<GamePaymentTypePrice> findByGameIdAndPaymentTypeId = gamePaymentTypePriceService.findByGameIdAndPaymentTypeId(gameId, paymentTypeId);
        System.out.println(findByGameIdAndPaymentTypeId.size());
        String currency = "USD";
        BigDecimal amount = new BigDecimal(5);
        GamePaymentTypePrice findByGameIdCurrencyAndAmount = gamePaymentTypePriceService.findByGameIdCurrencyAndAmount(gameId, paymentTypeId, currency, amount);
        String description = "abc";
        System.out.println(findByGameIdCurrencyAndAmount.getAmount());
        GamePaymentTypePrice findByGameIdCurrencyAndAmount2 = gamePaymentTypePriceService.findByGameIdCurrencyAndAmount(gameId, paymentTypeId, currency, amount, description);
        System.out.println(findByGameIdCurrencyAndAmount2.getAmount());

        SqlSession session = DbHelper.getInstance().getSqlSession();
        GamePaymentTypePriceDao gamePaymentTypePriceService = session.getMapper(GamePaymentTypePriceDao.class);
        
        List<GamePaymentTypePrice> select = gamePaymentTypePriceService.select();
        System.out.println(select.size());
        Integer gameId = 14;
        Integer paymentTypeId = 1;
        List<GamePaymentTypePrice> findByGameIdAndPaymentTypeId = gamePaymentTypePriceService.findByGameIdAndPaymentTypeId(gameId, paymentTypeId);
        System.out.println(findByGameIdAndPaymentTypeId.size());
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
        return 1;
    }

}
