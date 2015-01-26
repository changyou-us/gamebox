package com.gamebox.service.impl;

import java.util.HashMap;
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

@Service("snsServiceImpl")
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
    public Users getFacebookUser(String scopeId, String appId, String accessToken, Users newUsers) {

        Users users = getExistFacebookUser(scopeId, appId, accessToken);
        if (users != null) {// 不为空直接更新
            return users;
        }
        else {// 为空直接插入
            //newUsers.setNickname(scopeId + "gfacebookgcom");
            newUsers.setNickname(scopeId);
            usersDao.insert(newUsers);
            FacebookUserBusiness facebookUserBusiness = new FacebookUserBusiness();
            facebookUserBusiness.setAppId(appId);
            facebookUserBusiness.setScopeId(scopeId);
            facebookUserBusiness.setUser_id(newUsers.getUserId().toString());
            facebookUserBusinessDao.insert(facebookUserBusiness);
        }

        return newUsers;
    }

    @Override
    public Users getExistFacebookUser(String scopeId, String appId, String accessToken) {

        String email = scopeId + "@facebook.com";

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", accessToken);
        parameters.put("limit", "1000");

        String resultStr = WebUtils.sendGet(FACEBOOK_BUSSINESS_ENDPOINT, parameters);
        JSONObject json = JSONObject.fromObject(resultStr);
        JSONArray array = json.getJSONArray("data");
        System.out.println(array.size());
        FacebookUserBusiness facebookUserBusiness = facebookUserBusinessDao.findBusinessByScopeId(scopeId);
        JSONObject jo = null;
        if (facebookUserBusiness == null) {
            for (int i = 0; i < array.size(); i++) {
                jo = array.getJSONObject(i);
                System.out.println(jo.get("id"));
                scopeId = jo.getString("id");
                appId = jo.getJSONObject("app").getString("id");
                facebookUserBusiness = facebookUserBusinessDao.findBusinessByScopeId(scopeId);
                if (facebookUserBusiness != null) {
                    break;
                }
            }
        }
        

        Users users = null;
        if (facebookUserBusiness != null) {
            users = usersDao.findUserByUserId(facebookUserBusiness.getUser_id());
        }
        else {
            users = usersDao.findUserByEmail(email);
            if (users != null) {// 其实这种情况不应该出现，不过因为平台移植还是要更新
                facebookUserBusiness = new FacebookUserBusiness();
                facebookUserBusiness.setAppId(appId);
                facebookUserBusiness.setScopeId(scopeId);
                facebookUserBusiness.setUser_id(users.getUserId().toString());
                facebookUserBusinessDao.insert(facebookUserBusiness);
            }
            // else 就为null了
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
        String encodePassword = Md5Token.getInstance().getGameboxPassword(
                newUsers.getPassword(), newUsers.getSalt());
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
        facebookUserBusiness.setUser_id(newUsers.getUserId().toString());
        facebookUserBusinessDao.insert(facebookUserBusiness);
        return newUsers;
    }

}
