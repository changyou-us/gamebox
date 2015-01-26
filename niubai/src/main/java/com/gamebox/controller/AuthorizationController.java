package com.gamebox.controller;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamebox.model.FacebookAppInformation;
import com.gamebox.model.GamePaymentTypePrice;
import com.gamebox.model.Server;
import com.gamebox.model.Users;
import com.gamebox.service.FacebookAppInformationService;
import com.gamebox.service.GamePaymentTypePriceService;
import com.gamebox.service.GameService;
import com.gamebox.service.SNSService;
import com.gamebox.service.ServerService;
import com.gamebox.service.UsersService;
import com.gamebox.util.Base64Util;
import com.gamebox.util.FileUtils;
import com.gamebox.util.WebUtils;

@Controller
@RequestMapping("/facebook")
public class AuthorizationController {
    
    @Resource(name = "facebookAppInformationServiceImpl")
    private FacebookAppInformationService facebookAppInformationService;

    @Resource(name = "serverServiceImpl")
    private ServerService serverService;

    @Resource(name = "gameServiceImpl")
    private GameService gameService;
    
    @Resource(name = "usersService")
    private UsersService usersService;
    
    @Resource(name = "snsServiceImpl")
    private SNSService snsService;
    
    @Resource(name = "gamePaymentTypePriceServiceImpl")
    private GamePaymentTypePriceService gamePaymentTypePriceService;
    
    @RequestMapping("/authorization")
    public String authorization(HttpServletRequest request) {
        System.out.println("/////////////////////////////facebook get");
        Enumeration<?> attributeNames = request.getParameterNames();
        
        while(attributeNames.hasMoreElements()){
            Object nextElement = attributeNames.nextElement();
            System.out.println(nextElement.toString() + ":" + request.getParameter(nextElement.toString()));
        }
        System.out.println("/////////////////////");
        
        if ("credits".equals(request.getParameter("action"))) {
            return "redirect:/facebook/terran/credits.html";
        }
        return "redirect:/facebook/terran/index.html";
    }
    
    @RequestMapping("/authorization/{identifier}")
    public String authorizations(HttpServletRequest request, HttpServletResponse response, @PathVariable String identifier, String credits, ModelMap model) throws Exception{
        if (credits != null) {
            FacebookAppInformation facebookAppInformation = facebookAppInformationService.getFacebookAppInformation(identifier);
            String appId = facebookAppInformation.getAppId();
            Integer gameId = facebookAppInformation.getGameId();
            
            List<GamePaymentTypePrice> priceList = gamePaymentTypePriceService.findByGameIdAndPaymentTypeId(gameId, 8);
            List<Server> serverList = serverService.findByGameId(gameId, true);
            model.addAttribute("identifier", identifier);
            model.addAttribute("appId", appId);
            model.addAttribute("gameId", gameId);
            model.addAttribute("priceList", priceList);
            model.addAttribute("serverList", serverList);
            
            
            return "/webgame/fbgames/credits/credits_fb";
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Enumeration<?> attributeNames = request.getParameterNames();
        
        while(attributeNames.hasMoreElements()){
            Object nextElement = attributeNames.nextElement();
            System.out.println(nextElement.toString() + ":" + request.getParameter(nextElement.toString()));
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        
        FacebookAppInformation facebookAppInformation = facebookAppInformationService.getFacebookAppInformation(identifier);
        String appId = facebookAppInformation.getAppId();
        Integer gameId = facebookAppInformation.getGameId();
        String signed_request = request.getParameter("signed_request"); 
        String canvasPage = request.getParameter("canvas_page");
        String rccId = request.getParameter("rcc_id");
        String backUrl = "http://apps.facebook.com/" + canvasPage + "?rcc_id="+rccId;
        String authUrl ="https://www.facebook.com/dialog/oauth?client_id=" + appId + "&redirect_uri="+URLEncoder.encode(backUrl,"UTF-8");

        String[] sList = signed_request.split("\\.");
        String from = "-_";
        String   to = "+/";
        String str  = sList[1];
        for( int i = 0; i < from.length(); i++ )
        {
          str = str.replace(from.charAt(i), to.charAt(i));
        }
        
      //  list($encoded_sig, $payload) = explode('.', $signed_request, 2); 

       // $data = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);
       
        String json = Base64Util.decode(str, "UTF-8");
        System.out.println(json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        String scopeId = (String) jsonObject.get("user_id");
        
        if (scopeId == null) {
            System.out.println("11111111");
            model.addAttribute("authUrl", authUrl);
            return "/webgame/fbgames/auth_fb";
        } else {
            String accessToken  = (String) jsonObject.get("oauth_token");
            Users user = snsService.getExistFacebookUser(scopeId, appId, accessToken);
            System.out.println("222222");
            if (user == null) {
                Users newUsers = snsService.saveFacebookUser(scopeId, appId, request);
                setLoginInfo2Cache(newUsers, request, response);
                System.out.println("333333");
                if (rccId != null) {
                    System.out.println("444444");
                    // writelog
                    FileUtils.writeLog(request, "guanggao.log", rccId, newUsers.getUserId());
                }
                //return "redirect:/ad/" + rccId + ".html";
            } else {
                System.out.println("555555");
                if (rccId != null) {
                    System.out.println("666666");
                    FileUtils.writeLog(request, "guanggao.log", rccId, user.getUserId());
                }
                setLoginInfo2Cache(user, request, response);
                //return "redirect:/facebook/games/game_fb_" + identifier + ".html";
            }
            
        }
        
        System.out.println("77777");
        List<Server> servers = serverService.findByGameId(facebookAppInformation.getGameId(), true);
        
        model.addAttribute("servers", servers);
        model.addAttribute("identifier", identifier);
        model.addAttribute("appId", appId);
        model.addAttribute("gameId", gameId);
        model.addAttribute("canvasPage", canvasPage);
        return "/webgame/fbgames/" + identifier + "/server_fb_" + identifier;
    }
    
    
    private void setLoginInfo2Cache(Users user, HttpServletRequest request,
            HttpServletResponse response) {
        request.getSession().setAttribute(WebUtils.USER, user);
        String auth = WebUtils.authCode(
                user.getPassword() + "\t" + user.getEmail(),
                WebUtils.ENCODE_PASSWORD, null, 0);
        // cookie的生命周期
        WebUtils.addCookie(request, response, WebUtils.COOKIE_PREFIX
                + WebUtils.COOKIE_AUTH, auth, WebUtils.COOKIE_AGE);
        WebUtils.addCookie(request, response, WebUtils.COOKIE_PREFIX
                + WebUtils.COOKIE_USER, user.getEmail(), WebUtils.COOKIE_AGE);
        WebUtils.addCookie(request, response, WebUtils.COOKIE_PREFIX
                + WebUtils.COOKIE_UID, user.getUserId().toString(), WebUtils.COOKIE_AGE);
        // cu.setCookie(Constants.COOKIE_PREFIX + Constants.GAMEID,
        // gameId.toString(), 31536000);
    }
    
}
