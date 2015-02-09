package com.gamebox.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamebox.dao.FacebookAppInformationDao;
import com.gamebox.dao.FacebookUserBusinessDao;
import com.gamebox.dao.UsersDao;
import com.gamebox.model.FacebookUserBusiness;
import com.gamebox.model.Users;
import com.gamebox.model.AccountType;
import com.gamebox.service.SNSService;
import com.gamebox.util.CountryUtils;
import com.gamebox.util.DateUtils;
import com.gamebox.util.HttpUtils;
import com.gamebox.util.Md5Token;
import com.gamebox.util.ValidateUtils;
import com.gamebox.util.WebUtils;

/**
 * 第三方信息操作的service实现类
 * 
 * @author xiaoxiao
 * @since 2014-07-10
 */

@Service
public class SNSServiceImpl implements SNSService {

    private static final String FACEBOOK_BUSSINESS_ENDPOINT = "https://graph.facebook.com/v2.2/me/ids_for_business";

    /**
     * FB Dao
     */

    @Autowired
    private FacebookAppInformationDao facebookAppInformationDao;

    @Autowired
    private FacebookUserBusinessDao facebookUserBusinessDao;
    
    /**
     * 用户 Dao
     */
    @Autowired
    private UsersDao usersDao;

    @Override
    public Users getFacebookUser(String scopeId, String appId, String accessToken, HttpServletRequest request) {

        Users users = getExistFacebookUser(scopeId, appId, accessToken);
        if (users == null) {// 不为空直接更新
            users = saveFacebookUser(scopeId, appId, request);
        }

        return users;
    }

    @Override
    public Users getExistFacebookUser(String scopeId, String appId, String accessToken) {
        Date date = new Date();
        Users users = null;

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", accessToken);
        parameters.put("limit", "1000");

        String resultStr = WebUtils.sendGet(FACEBOOK_BUSSINESS_ENDPOINT, parameters);
        JSONObject json = JSONObject.fromObject(resultStr);
        JSONArray array = json.getJSONArray("data");
        System.out.println(array.size());
        FacebookUserBusiness facebookUserBusiness = facebookUserBusinessDao.findBusinessByScopeId(scopeId);
        JSONObject jo = null;
        List<String> list = new ArrayList<String>();
        List<String> emailsList = new ArrayList<String>();
        if (facebookUserBusiness == null) {
            System.out.println("for");
            for (int i = 0; i < array.size(); i++) {
                jo = array.getJSONObject(i);
                System.out.println(jo.get("id"));
                String iteratorScopeId = jo.getString("id");
                list.add(iteratorScopeId);
                emailsList.add(iteratorScopeId + "@facebook.com");
            }
            
            facebookUserBusiness  = facebookUserBusinessDao.findBusinessByScopeIdList(list);
            if (facebookUserBusiness == null) {
                users = usersDao.findUserByEmailsList(emailsList);
                if (users != null) {// 其实这种情况不应该出现，不过因为平台移植还是要更新
                    facebookUserBusiness = new FacebookUserBusiness();
                    facebookUserBusiness.setAppId(appId);
                    facebookUserBusiness.setScopeId(scopeId);
                    facebookUserBusiness.setUserId(users.getUserId().toString());
                    facebookUserBusiness.setCreateDate(date);
                    facebookUserBusiness.setModifyDate(date);
                    facebookUserBusinessDao.insert(facebookUserBusiness);
                }
            } else {
                FacebookUserBusiness newFacebookUserBusiness = new FacebookUserBusiness();
                newFacebookUserBusiness.setAppId(appId);
                newFacebookUserBusiness.setScopeId(scopeId);
                newFacebookUserBusiness.setUserId(facebookUserBusiness.getUserId());
                newFacebookUserBusiness.setCreateDate(date);
                newFacebookUserBusiness.setModifyDate(date);
                facebookUserBusinessDao.insert(newFacebookUserBusiness);
                
                users = usersDao.findUserByUserId(facebookUserBusiness.getUserId());
            }
        } else {
            users = usersDao.findUserByUserId(facebookUserBusiness.getUserId());
        }
        
        return users;
    }

    @Override
    public Users saveFacebookUser(String scopeId, String appId, HttpServletRequest request) {

        Users newUsers = new Users();
        newUsers.setEmail(scopeId + "@facebook.com");
        newUsers.setAccountType(AccountType.facebook);
        // 设置注册的信息
        String regIp = HttpUtils.getIp(request);
        newUsers.setRegIp(regIp);
        newUsers.setSalt(ValidateUtils.getRandStr(6, false));
        String encodePassword = Md5Token.getInstance().getGameboxPassword(newUsers.getPassword(), newUsers.getSalt());
        
        newUsers.setPassword(encodePassword);
        newUsers.setRegCountry(CountryUtils.getCountry(regIp));
        newUsers.setRegTime(DateUtils.getCurrentTime());
        newUsers.setLastLogin(DateUtils.getCurrentTime());
        newUsers.setLoginNum(0);
        newUsers.setAccountType(AccountType.facebook);
        newUsers.setBlacklist(false);
        newUsers.setNickname(scopeId);
        usersDao.insert(newUsers);
        FacebookUserBusiness facebookUserBusiness = new FacebookUserBusiness();
        facebookUserBusiness.setAppId(appId);
        facebookUserBusiness.setScopeId(scopeId);
        facebookUserBusiness.setUserId(newUsers.getUserId().toString());
        facebookUserBusinessDao.insert(facebookUserBusiness);
        return newUsers;
    }

}
