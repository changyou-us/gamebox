package com.gamebox.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gamebox.dao.DirectPaymentOrderDao;
import com.gamebox.dao.GamePaymentTypePriceDao;
import com.gamebox.dao.ServerDao;
import com.gamebox.dao.UsersDao;
import com.gamebox.model.DirectPaymentOrder;
import com.gamebox.model.GamePaymentTypePrice;
import com.gamebox.model.OrderStatus;
import com.gamebox.model.Server;
import com.gamebox.model.DisplayType;
import com.gamebox.model.TransIdUsedStatus;
import com.gamebox.model.Users;
import com.gamebox.model.OpenStatusType;
import com.gamebox.service.GameService;
import com.gamebox.service.ServerService;
import com.gamebox.util.Base64Util;
import com.gamebox.util.DateUtils;
import com.gamebox.util.Md5Token;
import com.gamebox.util.WebUtils;

/**
 * Service - 管理员
 * 
 * @author Dick niu
 * @version 1.0a
 */ 
@Service
public class ServerServiceImpl implements ServerService{

    public static final String IS_NEW_TYPE = "isNew";

    public static final String RECOMMENDED_TYPE = "recommended";

    // 占位符，替换成serverId
    private static final String LOGIN_GAME_SERVERID_STRING = "$(sid)";

    // 占位符，替换成showId
    private static final String LOGIN_GAME_SHOWID_STRING = "$(showid)";

    // 占位符，替换成serverId数字加一个整数
    private static final String LOGIN_GAME_SERVERID_LONG = "\\$\\(sid\\+\\d*\\)";

    // 占位符，替换成系统时间戳
    private static final String LOGIN_GAME_TIMESTAMP = "$(time)";

    // 占位符，替换成userId
    private static final String LOGIN_GAME_USERID = "$(uid)";

    // 占位符，替换成昵称
    private static final String LOGIN_GAME_NICK = "$(nick)";

    // 占位符，对loginSecret进行32位加密并转成大写
    private static final String LOGIN_GAME_OAUTH_UPPER = "$(OAUTH)";

    // 占位符，替换成publicKey
    private static final String LOGIN_GAME_PUBLICKEY = "$(key)";

    // 占位符，对loginSecret进行32位加密
    private static final String LOGIN_GAME_OAUTH = "$(oauth)";

    // 占位符，对方括号中的内容进行64位加密
    private static final String LOGIN_GAME_AUTH64 = "\\$auth64\\[.*\\]";

    // 占位符，替换成一个随机数
    private static final String LOGIN_GAME_UUID = "$(uuid)";

    // 占位符，替换成排行榜类型
    private static final String RANK_GAME_TYPE = "$(type)";

    // 占位符，替换成昨天的日期
    private static final String RANK_GAME_YESTERDAY = "$(yesterday)";

    @Autowired
    private DirectPaymentOrderDao directPaymentOrderDao;

    @Autowired
    private ServerDao serverDao;

    @Autowired
    private GameService gameService;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private GamePaymentTypePriceDao gamePaymentTypePriceDao;

    @Override
    public boolean serverExists(Integer serverId, Integer gameId, OpenStatusType openStatus, DisplayType display) {

        return serverDao.serverExists(serverId, gameId, openStatus, display);
    }

    @Override
    public Server findServerByGidAndSid(Integer gid, Integer sid) {

        return serverDao.findServerByGidAndSid(gid, sid);
    }

    @Cacheable("server")
    @Override
    public List<Server> findByGameId(Integer gid, Boolean invalid) {

        return serverDao.findByGameId(gid, invalid);
    }

    @Override
    public String buildGameUrl(Integer gameId, Integer serverId, Users user) {

        Server server = serverDao.findServerByGidAndSid(gameId, serverId);
        String randId = java.util.UUID.randomUUID().toString();
        String time = DateUtils.getCurrentTime().toString();
        String oauth = "";
        // 1.判断Gamebox, newGamebox, Gamefuse 或者Tagame用户
        String playerId = gameService.getPlayerId(gameId, user);
        if (playerId == null) {
            return null;
        }
        
        String url = specialGamesBuildUrl(gameId, serverId, playerId);
        if (url != null) {
            return url;
        }
        // 2.解析loginSecret
        String loginSecret = server.getLoginSecret();
        // 合服后是否启用transferedId
        Integer sid = server.getTransIdUsedStatus() == TransIdUsedStatus.NOT_USED ? server.getServerId()
                : server.getTransferedId();
        String showId = server.getShowId() == null ? "" : Integer.toString(server.getShowId());
        System.out.println(loginSecret);
        System.out.println(sid.toString());
        System.out.println(showId);
        System.out.println(playerId);
        System.out.println(randId);
        System.out.println(time);
        System.out.println(server.getPublicKey());
        System.out.println(user.getNickname());
        loginSecret = replaceUrl(loginSecret, sid.toString(), showId, playerId, randId, time, server.getPublicKey(),
                oauth, "", user.getNickname(), "");
        // 3.对loginSecret加密
        oauth = Md5Token.getInstance().getLongToken(loginSecret);
        // 4.校验(添加特殊游戏的业务规则)
        if (!verify(gameId.toString(), sid.toString(), playerId, randId, time, oauth, showId)) {
            return null;
        }
        // 5.解析loginUrl
        return replaceUrl(server.getLoginUrl(), sid.toString(), showId, playerId, randId, time, server.getPublicKey(),
                oauth, "", user.getNickname(), "");
    }

    private String specialGamesBuildUrl(Integer gameId, Integer serverId, String playerId) {

        try {
            if (gameId == 21) {
                Server server = serverDao.findServerByGidAndSid(gameId, serverId);
                String account = playerId;
                String sid = serverId.toString();
                String time = DateUtils.getCurrentTime().toString();
                String helpUrl = URLEncoder.encode("https://www.facebook.com/tidaltrekofficial", "UTF-8");
                String forumUrl = URLEncoder.encode("https://www.facebook.com/tidaltrekofficial", "UTF-8");
                String favoriteUrl = URLEncoder.encode("https://www.facebook.com/tidaltrekofficial", "UTF-8");
                String payUrl = URLEncoder.encode("https://apps.facebook.com/tidal-trek/?credits=1", "UTF-8");
                String key = server.getPublicKey();

                Map<String, String> paramMap = new TreeMap<String, String>();
                paramMap.put("account", account);
                paramMap.put("sid", sid);
                paramMap.put("time", time);
                paramMap.put("helpUrl", helpUrl);
                paramMap.put("forumUrl", forumUrl);
                paramMap.put("favoriteUrl", favoriteUrl);
                paramMap.put("payUrl", payUrl);

                String urlParams = "";
                for (Entry<String, String> e : paramMap.entrySet()) {
                    urlParams += e.getKey() + "=" + e.getValue() + "&";
                }
                urlParams = urlParams.substring(0, urlParams.length() - 1);
                String sign = DigestUtils.md5Hex(urlParams + key);
                String url = "https://tt-test.gamebox.com/index.php?" + urlParams + "&sign=" + sign;

                return url;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private boolean verify(String string, String string2, String playerId, String randId, String time, String oauth,
            String showId) {

        return true;
    }

    @Override
    public String buildRankUrl(Integer gameId, Integer serverId, String type) {

        Server server = serverDao.findServerByGidAndSid(gameId, serverId);
        String time = DateUtils.getCurrentTime().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String yesterday = sdf.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        String oauth = "";
        // 1.解析rankSecret
        String rankSecret = server.getRankSecret();
        // 合服后是否启用transferedId
        Integer sid = server.getTransIdUsedStatus() == TransIdUsedStatus.NOT_USED ? server.getServerId()
                : server.getTransferedId();
        String showId = server.getShowId() == null ? "" : Integer.toString(server.getShowId());
        if (rankSecret != null && !"".equals(rankSecret)) {
            rankSecret = replaceUrl(rankSecret, sid.toString(), showId, "", "", time, server.getRankKey(), oauth, type,
                    "", yesterday);
            oauth = Md5Token.getInstance().getLongToken(rankSecret);
        }
        return replaceUrl(server.getRankUrl(), sid.toString(), showId, "", "", time, server.getRankKey(), oauth, type,
                "", yesterday);
    }

    // 0-orginalUrl, 1-serverId, 2-showId, 3-userId, 4-randId, 5-time, 6-key, 7-oauth, 8-type, 9-nick, 10-yesterday
    
    private String replaceUrl(String... args) {

        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                args[i] = "";
            }
        }

        // 1. 将字符串进行初步直接替换
        String url = args[0].replace(LOGIN_GAME_SERVERID_STRING, args[1]).replace(LOGIN_GAME_USERID, args[3])
                .replace(LOGIN_GAME_TIMESTAMP, args[5]).replace(LOGIN_GAME_PUBLICKEY, args[6])
                .replace(LOGIN_GAME_SHOWID_STRING, args[2]).replace(LOGIN_GAME_OAUTH, args[7])
                .replace(LOGIN_GAME_UUID, args[4]).replace(RANK_GAME_TYPE, args[8]).replace(LOGIN_GAME_NICK, args[9])
                .replace(LOGIN_GAME_OAUTH_UPPER, args[7].toUpperCase()).replace(RANK_GAME_YESTERDAY, args[10]);
        // 2. 处理数字相加类型
        url = this.repalceDigitServerID(url, Long.parseLong(args[1]), 0);
        // 3. 处理嵌套类型
        url = replaceAuth64(url, 0);
        return url;
    }

    private String repalceDigitServerID(String url, long serverId, int index) {

        // index起到保护作用无论怎样都不让死循环形成
        if (!url.matches("^.*\\$\\(sid\\+\\d*\\).*$") || index > 9) {
            return url;
        }
        long digit = Long.parseLong(url.substring(url.indexOf("$(sid+") + "$(sid+".length(), url.indexOf(")")));
        return repalceDigitServerID(url.replaceFirst(LOGIN_GAME_SERVERID_LONG, digit + serverId + ""), serverId,
                index++);
    }

    private String replaceAuth64(String url, int index) {

        if (!url.matches("^.*\\$auth64\\[.*\\].*$") || index > 9) {
            return url;
        }
        String subStr = url.substring(url.indexOf("$auth64[") + "$auth64[".length(), url.indexOf("]"));
        return replaceAuth64(url.replaceFirst(LOGIN_GAME_AUTH64, Base64Util.encode(subStr, "UTF-8")), index++);
    }

    @Override
    public int gamesCharge(DirectPaymentOrder directPaymentOrder) {

        if (directPaymentOrder.getStatus().equals(OrderStatus.COMPLETED)) {
            return 1;
        }
        Integer gameId = directPaymentOrder.getGameId();
        Integer serverId = directPaymentOrder.getServerId();
        Integer paymentTypeId = directPaymentOrder.getPaymentTypeId();
        
        String amount = String.valueOf(directPaymentOrder.getConvertAmount());
        String paymentName = "facebook";
        String currency = directPaymentOrder.getCurrency();
        GamePaymentTypePrice gamePaymentTypePrice = null;
        if (gameId == 19) {
            gamePaymentTypePrice = gamePaymentTypePriceDao.findByGameIdPaymentTypeIdCurrencyAmountAndDescription(gameId, paymentTypeId,
                    currency, directPaymentOrder.getAmount(), directPaymentOrder.getDescription());
        }
        else {
            gamePaymentTypePrice = gamePaymentTypePriceDao.findByGameIdPaymentTypeIdCurrencyAndAmount(gameId, paymentTypeId,
                    currency, directPaymentOrder.getAmount());
        }
        Integer gameCoin = gamePaymentTypePrice.getGameCoin();
        Integer coinBonus = gamePaymentTypePrice.getCoinBonus();
        Integer gameCoins = gameCoin + coinBonus;
        String ordersn = directPaymentOrder.getOrderSn();
        String roleId = directPaymentOrder.getRoleId();
        String roleName = directPaymentOrder.getRoleName();
        String description = directPaymentOrder.getDescription();
        return this.recharge(gameId, serverId, directPaymentOrder.getUserId(), amount, ordersn, roleId, roleName,
                gameCoins, gameCoin, coinBonus, paymentName, currency, true, description);

    }

    @Override
    public int recharge(Integer gameId, Integer serverId, Integer userId, String amount, String ordersn, String roleId,
            String roleName, Integer gameCoins, Integer gameCoin, Integer bonus, String paymentName, String currency,
            boolean isValidate, String description) {

        String sid = serverId.toString();
        Server server = serverDao.findServerByGidAndSid(gameId, serverId);
        String uid = userId.toString();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String showId = server.getShowId().toString();
        String key = server.getRechargeKey();
        String secret = server.getRechargeSecret();
        String expectResult = server.getRechargeSign();
        String auth = "";
        if (isValidate) {
            uid = gameService.getPlayerId(gameId, usersDao.findUserByUserId(uid));
        }

        if (server.getTransIdUsedStatus().equals(TransIdUsedStatus.USED)) {
            serverId = server.getTransferedId();
            sid = server.getTransferedId().toString();
        }

        if (gameId == 19) {
            int timestamp = DateUtils.getCurrentTime();
            // timestamp += 300;//timestamp 加多300秒，请求不容易失效 -博雅支付
            String partner = "by001200640290";
            int coins = 0, chips = 0, prod = 0;
            if ("Boyaa".equals(description)) {
                coins = gameCoins;
            }
            else if ("Chips".equals(description)) {
                chips = gameCoins;
            }

            Map<String, String> paramMap = new TreeMap<String, String>();
            paramMap.put("parnter", partner);
            paramMap.put("account", uid);
            paramMap.put("orderid", ordersn);
            paramMap.put("amount", amount);
            paramMap.put("coins", String.valueOf(coins));
            paramMap.put("chips", String.valueOf(chips));
            paramMap.put("prod", String.valueOf(prod));
            paramMap.put("timestamp", String.valueOf(timestamp));
            String urlPara = "";
            for (Entry<String, String> e : paramMap.entrySet()) {
                urlPara += e.getKey() + "=" + e.getValue() + "&";
            }
            String urlParams = urlPara.substring(0, urlPara.length() - 1);

            String sign = DigestUtils.md5Hex(urlParams + key);

            String url = "http://pay.boyaa.com/api_pay_order.php?" + urlParams + "&sign=" + sign;

            String res = WebUtils.sendGet(url, null);
            System.out.println(res);
            JSONObject json = JSONObject.fromObject(res);

            return Integer.parseInt(json.getString("ret"));
        }

        if (gameId == 8 || gameId == 6) {
            ordersn = description;
        }
        else if (gameId == 14) {
            gameCoin *= 10;
            gameCoins *= 10;
            bonus *= 10;
            if (serverId > 21 && serverId < 1000) {// 22服以后平台和android都开混服，按android的游戏服命名规则，游戏接口传1022 1023……,不再有22
                                                   // 23单独传入游戏接口的情况
                serverId += 1000;
            }
        }
        else if (gameId == 10) {
            time = DateUtils.getCurrentDay("yyyyMMddHHmmss");
        }
        else if (gameId == 17) {
            sid = String.valueOf(300000 + serverId);
            showId = serverId.toString();
            gameCoins -= 1;
        }
        else if (gameId == 18) {
            sid = String.valueOf((57007010000000L + (long) serverId));
        }

        try {
            roleName = URLEncoder.encode(roleName, "UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
            roleName = "NULL";
        }
        if (gameId != 18) {
            secret = secret.replace("(key)", key).replace("(sid)", sid).replace("(showid)", showId)
                    .replace("(uid)", uid).replace("(time)", time).replace("(amount)", amount)
                    .replace("(ordersn)", ordersn).replace("(coin)", gameCoin.toString())
                    .replace("(coins)", gameCoins.toString()).replace("(bonus)", bonus.toString())
                    .replace("(currency)", currency).replace("(payment)", paymentName).replace("(roleid)", roleId)
                    .replace("(rolename)", roleName);
            System.out.println(secret);
            secret = DigestUtils.md5Hex(secret);
        }
        else {
            auth = secret.replace("(key)", key).replace("(sid)", sid).replace("(showid)", showId).replace("(uid)", uid)
                    .replace("(time)", time).replace("(amount)", amount).replace("(ordersn)", ordersn)
                    .replace("(coin)", gameCoin.toString()).replace("(coins)", gameCoins.toString())
                    .replace("(bonus)", bonus.toString()).replace("(currency)", currency)
                    .replace("(payment)", paymentName).replace("(roleid)", roleId).replace("(rolename)", roleName);
            System.out.println(auth);
            auth = Base64Util.encode(auth, "UTF-8");
            secret = DigestUtils.md5Hex(auth + key);
        }
        // secret = DigestUtils.md5DigestAsHex(secret.replace("(key)", key).replace("(sid)", sid).replace("(uid)",
        // uid).replace("(time)", time)
        // .getBytes());
        String url = server.getRechargeUrl();
        url = url.replace("(sid)", sid).replace("(showid)", showId).replace("(uid)", uid).replace("(time)", time)
                .replace("(amount)", amount).replace("(ordersn)", ordersn).replace("(coin)", gameCoin.toString())
                .replace("(coins)", gameCoins.toString()).replace("(bonus)", bonus.toString())
                .replace("(secret)", secret).replace("(currency)", currency).replace("(payment)", paymentName)
                .replace("(roleid)", roleId).replace("(rolename)", roleName).replace("(auth)", auth);

        String responseStr = WebUtils.sendGet(url, null);
        if (gameId.equals(9)) {
            responseStr = responseStr.split("<ret>")[1].split("</ret>")[0];
        }
        else if (gameId.equals(18)) {
            responseStr = JSONObject.fromObject(responseStr).getString("status");
        }
        System.out.println(responseStr);
        System.out.println(expectResult);
        if (expectResult.equals(responseStr.trim())) {
            if (gameId == 4) {
                //oqRewards(serverId, userId, uid, roleName, gameCoin);
            }
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public Integer getNewestServerId(Integer gameId){
        return serverDao.getNewestServerId(gameId);
    }



}
