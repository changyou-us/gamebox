package com.gamebox.controller;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private FacebookAppInformationService facebookAppInformationService;

    @Autowired
    private ServerService serverService;

    @Autowired
    private GameService gameService;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private SNSService snsService;
    
    @Autowired
    private GamePaymentTypePriceService gamePaymentTypePriceService;
    
    @RequestMapping("/authorization/{identifier}")
    public String authorizations(HttpServletRequest request, HttpServletResponse response, @PathVariable String identifier, String credits, ModelMap model) throws Exception{

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
        String rccId = request.getParameter("rcc_id");
        String backUrl = "http://apps.facebook.com/" + appId + "?rcc_id="+rccId;
        String authUrl ="https://www.facebook.com/dialog/oauth?client_id=" + appId + "&redirect_uri="+URLEncoder.encode(backUrl,"UTF-8") + "&auth_type=rerequest&scope=email,user_friends";

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
        String scopeId = (String)jsonObject.get("user_id");
        
        if (scopeId == null) {
            System.out.println("11111111");
            model.addAttribute("authUrl", authUrl);
            return "/auth_fb";
        } 
        
        String accessToken  = (String) jsonObject.get("oauth_token");
        String inspecting = WebUtils.sendGet("https://graph.facebook.com/debug_token?input_token=" + accessToken + "&access_token=" + facebookAppInformation.getAppToken(), null);
        try {
            System.out.println(inspecting);
            if (!scopeId.equals(JSONObject.fromObject(inspecting).getJSONObject("data").getString("user_id"))) {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Users user = snsService.getExistFacebookUser(scopeId, appId, accessToken);
        
        if (user == null) {
            user = snsService.saveFacebookUser(scopeId, appId, request);
                //return "redirect:/ad/" + rccId + ".html";
        }
            
        if (rccId != null) {
            FileUtils.writeLog(request, "guanggao.log", rccId, user.getUserId());
            return "redirect:https://" + request.getServerName() + "/ad/" + rccId + ".html";
        }
            
        setLoginInfo2Cache(user, request, response);
            //return "redirect:/ad/" + rccId + ".html";
        
        
        List<Server> serverList = serverService.findByGameId(facebookAppInformation.getGameId(), true);
        
        model.addAttribute("serverList", serverList);
        model.addAttribute("identifier", identifier);
        model.addAttribute("appId", appId);
        model.addAttribute("gameId", gameId);
        if (credits != null) {
            
            List<GamePaymentTypePrice> priceList = gamePaymentTypePriceService.findByGameIdAndPaymentTypeId(gameId, 8);
            model.addAttribute("priceList", priceList);
            
            return "/credits/credits_fb";
        }
        return "/" + identifier + "/servers_" + identifier;
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
