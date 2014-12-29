package com.gamebox.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gamebox.dao.ServerDao;
import com.gamebox.model.Server;
import com.gamebox.model.Server.DisplayType;
import com.gamebox.model.Server.IsNewType;
import com.gamebox.model.Server.RecommendedType;
import com.gamebox.model.Users;
import com.gamebox.model.Webgame;
import com.gamebox.model.Webgame.OpenStatusType;
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
 * @version 1.0
 */
@Service("serverServiceImpl")
public class ServerServiceImpl  implements ServerService {

    public static final String IS_NEW_TYPE = "isNew";

    public static final String RECOMMENDED_TYPE = "recommended";

    // 特殊游戏ID 5 - Wartune
    private static final String LOGIN_GAME_WARTUNE = "5";

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

    @Resource(name = "directPaymentOrderDaoImpl")
    private DirectPaymentOrderDao directPaymentOrderDao;

    @Resource(name = "serverDaoImpl")
    private ServerDao serverDao;

    @Resource(name = "staticServiceImpl")
    private StaticService staticService;

    @Resource(name = "gameServiceImpl")
    private GameService gameService;

    @Resource(name = "usersDao")
    private UsersDao usersDao;

    @Resource(name = "userPlayInfoDaoImpl")
    private UserPlayInfoDao userPlayInfoDao;

    @Resource(name = "gamePaymentTypePriceDaoImpl")
    private GamePaymentTypePriceDao gamePaymentTypePriceDao;

    @Resource(name = "gameTokenPriceDaoImpl")
    private GameTokenPriceDao gameTokenPriceDao;

    @Resource(name = "paymentTypeServiceImpl")
    protected PaymentTypeService paymentTypeService;

    @Resource(name = "tokenExchangeOrderDaoImpl")
    private TokenExchangeOrderDao tokenExchangeOrderDao;


    @Transactional(readOnly = true)
    public Server find(Long id) {

        return null;
    }

    @Transactional
    @CacheEvict(value = { "server" }, allEntries = true)
    public void save(Server server) {

//        Assert.notNull(server);
//
//        super.save(server);
//        serverDao.flush();
//
//        if (server.getDisplay() != null) {
//            if (server.getDisplay() == DisplayType.display) {
//
//            }
//        }
    }

    @Transactional(readOnly = true)
    public boolean serverExists(Integer serverId, Integer gameId) {

        return serverDao.serverExists(serverId, gameId);
    }

    @Transactional(readOnly = true)
    public boolean serverExists(Integer serverId, Integer gameId, OpenStatusType openStatus, DisplayType display) {

        return serverDao.serverExists(serverId, gameId, openStatus, display);
    }

    @Transactional
    public boolean updateImm(Integer serverId, Integer gameId, String pName, Object pValue) {

        boolean result = false;
        if (IS_NEW_TYPE.equals(pName)) {
            if (IsNewType.fresh.name().equals(pValue.toString())) {
                result = serverDao.updateIsNew(serverId, gameId, IsNewType.fresh);
            }
            else if (IsNewType.old.name().equals(pValue.toString())) {
                result = serverDao.updateIsNew(serverId, gameId, IsNewType.old);
            }

        }
        else if (RECOMMENDED_TYPE.equals(pName)) {
            if (RecommendedType.recommended.name().equals(pValue.toString())) {
                result = serverDao.updateRecommended(serverId, gameId, RecommendedType.recommended);
            }
            else if (RecommendedType.notRecommended.name().equals(pValue.toString())) {
                result = serverDao.updateRecommended(serverId, gameId, RecommendedType.notRecommended);
            }
        }

        // 重新生成服务器列表模板
        // staticService.build(serverDao.findByGameId(gameId, true), gameId);
        return result;
    }

    @Transactional
    @CacheEvict(value = { "server" }, allEntries = true)
    public Server update(Server server) {

        Server result = super.update(server);
        // staticService.build(serverDao.findByGameId(server.getGameId(), false), server.getGameId());
        return result;
    }

    @Transactional
    @CacheEvict(value = { "server" }, allEntries = true)
    public void delete(Long... ids) {

        super.delete(ids);
    }

    @Transactional(readOnly = true)
    public Server findServerByGidAndSid(Integer gid, Integer sid) {

        return serverDao.findServerByGidAndSid(gid, sid);
    }

    @Transactional(readOnly = true)
    @Cacheable("server")
    public List<Server> findByGameId(Integer gid, Boolean invalid) {

        return serverDao.findByGameId(gid, invalid);
    }

    @Transactional(readOnly = true)
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
        // 2.解析loginSecret
        String loginSecret = server.getLoginSecret();
        // 合服后是否启用transferedId
        Integer sid = server.getTransIdUsedStatus() == Server.TransIdUsedStatus.NOT_USED ? server.getServerId()
                : server.getTransferedId();
        String showId = server.getShowId() == null ? "" : Integer.toString(server.getShowId());
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

    @Transactional(readOnly = true)
    public String buildRankUrl(Integer gameId, Integer serverId, String type) {

        Server server = serverDao.findServerByGidAndSid(gameId, serverId);
        String time = DateUtils.getCurrentTime().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String yesterday = sdf.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        String oauth = "";
        // 1.解析rankSecret
        String rankSecret = server.getRankSecret();
        // 合服后是否启用transferedId
        Integer sid = server.getTransIdUsedStatus() == Server.TransIdUsedStatus.NOT_USED ? server.getServerId()
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

    private boolean verify(String gameId, String serverId, String userId, String randId, String time, String oauth,
            String showId) {

        if (gameId.equals(LOGIN_GAME_WARTUNE)) {
            if ("4".equals(serverId)) {
                serverId = "3";
            }
            String verfiyUrl = "http://s" + serverId + ".gamebox.com/createlogin?content=" + userId + "|" + randId
                    + "|" + time + "|" + oauth + "&site=cy_000" + showId;
            System.out.println(verfiyUrl);
            try {
                URL url = new URL(verfiyUrl);
                URLConnection urlConn = url.openConnection();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String msg = bfr.readLine();
                bfr.close();
                // 代理商登录不成功
                if (!"0".equals(msg)) {
                    return false;
                }
            }
            catch (MalformedURLException e) {
                return false;
            }
            catch (IOException e) {
                return false;
            }
        }

        return true;
    }

    public Map<String, String> getMapByServer(Server server) {

        Map<String, String> rmap = new HashMap<String, String>();
        if (server != null && server.getDisplay() == DisplayType.display) {
            rmap.put("serverId", server.getServerId().toString());
            rmap.put("isNew", server.getIsNew().toString());
            rmap.put("recommended", server.getRecommended().toString());
            rmap.put("timezone", server.getTimezone().name());
            rmap.put("name", server.getName());
            rmap.put("status", server.getStatus().name());
        }
        return rmap;
    }

    @Override
    public int gamesCharge(DirectPaymentOrder directPaymentOrder) {

        if (directPaymentOrder.getStatus().equals(OrderStatus.COMPLETED)) {
            return 1;
        }
        Integer gameId = directPaymentOrder.getGameId();
        Integer serverId = directPaymentOrder.getServerId();
        Integer paymentTypeId = directPaymentOrder.getPaymentTypeId();
        // String sid = serverId.toString();
        // Server server = serverService.findServerByGidAndSid(gameId, serverId);
        String amount = String.valueOf(directPaymentOrder.getConvertAmount());
        String paymentName = paymentTypeService.findByTypeId(paymentTypeId).getTypeName();
        String currency = directPaymentOrder.getCurrency();
        GamePaymentTypePrice gamePaymentTypePrice = null;
        if (gameId == 19) {
            gamePaymentTypePrice = gamePaymentTypePriceDao.findByGameIdCurrencyAndAmount(gameId, paymentTypeId,
                    currency, directPaymentOrder.getAmount(), directPaymentOrder.getDescription());
        }
        else {
            gamePaymentTypePrice = gamePaymentTypePriceDao.findByGameIdCurrencyAndAmount(gameId, paymentTypeId,
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
    public int gamesCharge(TokenExchangeOrder tokenExchangeOrder) {

        if (tokenExchangeOrder.getStatus().equals(OrderStatus.COMPLETED)) {
            return 1;
        }
        Integer gameId = tokenExchangeOrder.getGameId();
        Integer serverId = tokenExchangeOrder.getServerId();
        // String sid = serverId.toString();
        // Server server = serverService.findServerByGidAndSid(gameId, serverId);
        BigDecimal bigDecimal = new BigDecimal(tokenExchangeOrder.getTokenAmount());

        String amount = bigDecimal.divide(new BigDecimal(100)).toString();
        String paymentName = "Gtokens";
        GameTokenPrice gameTokenPrice = gameTokenPriceDao.findByGameIdAndTokenAmount(gameId,
                tokenExchangeOrder.getTokenAmount());

        Integer gameCoin = gameTokenPrice.getGameCoin();
        Integer coinBonus = gameTokenPrice.getCoinBonus();
        Integer gameCoins = gameCoin + coinBonus;
        String ordersn = tokenExchangeOrder.getOrderSn();
        String roleId = tokenExchangeOrder.getRoleId();
        String roleName = tokenExchangeOrder.getGameRole();
        String description = tokenExchangeOrder.getDescription();
        tokenExchangeOrder.setGameCoin(gameCoins);
        tokenExchangeOrderDao.persist(tokenExchangeOrder);
        return this.recharge(gameId, serverId, tokenExchangeOrder.getUserId(), amount, ordersn, roleId, roleName,
                gameCoins, gameCoin, coinBonus, paymentName, "USD", true, description);

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
            uid = gameService.getPlayerId(gameId, usersDao.findUserById(uid));
        }

        if (server.getTransIdUsedStatus().equals(Server.TransIdUsedStatus.USED)) {
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
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
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
                oqRewards(serverId, userId, uid, roleName, gameCoin);
            }
            return 1;
        }
        else {
            return 0;
        }
    }

    public int updateStatus(Long[] ids, Integer status) {

        return serverDao.updateStatus(ids, status);
    }

    private void oqRewards(Integer serverId, Integer userId, String uid, String roleName, Integer gameCoin) {
        try {
        int leaf = gameCoin;

        String sendtitle = "Event Rewards";

        String body = "Dear Player,Thanks so much for your support. Here are the recharge rewards. Please click to receive them. Have a nice day!Gamebox Team";

        String sDateTime = DateUtils.getCurrentDay("yyyy-MM-dd");

        String timestamp = DateUtils.getCurrentTime().toString();
        //
        String secret = DigestUtils.md5Hex(sDateTime + timestamp + "@#dj%sdgamecibboxu#m");

        if (serverId > 300 && serverId < 400) {
            secret = DigestUtils.md5Hex(sDateTime + timestamp + "@box%sduutcgameome#c");
        }

        String urlOfRole = "http://oqs" + serverId
                + ".gamebox.com/hoho_admin/interface/export/interface.php?method=characters&user_id=" + uid + "&date="
                + sDateTime + "&time=" + timestamp + "&flag=" + secret;
        // 时区服
        if (serverId > 200 && serverId < 300) {
            urlOfRole = "http://edtoqs" + (serverId - 200)
                    + ".gamebox.com/hoho_admin/interface/export/interface.php?method=characters&user_id=" + uid
                    + "&date=" + sDateTime + "&time=" + timestamp + "&flag=" + secret;
        }
        if (serverId > 300 && serverId < 400) {
            urlOfRole = "http://utcoqs" + (serverId - 300)
                    + ".gamebox.com/hoho_admin/interface/export/interface.php?method=characters&user_id=" + uid
                    + "&date=" + sDateTime + "&time=" + timestamp + "&flag=" + secret;
        }

        String rescc = WebUtils.sendGet(urlOfRole, null);

        JSONArray jsonArray = JSONArray.fromObject(rescc);

        String guid = "";

        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String nickname = jsonObject.getString("nickname");
            if (nickname.equalsIgnoreCase(roleName)) {
                guid = jsonObject.getString("Guid");
                System.out.println("=======pay Guid=======" + guid);
            }
        }

        if (guid.equals("")) {
            return;
        }
        List<DirectPaymentOrder> list = directPaymentOrderDao.oqRewardsOnCurrentDay(userId);
        String urlOfTool = "";
        String wupin = "";
        if (list.size() < 1) {
            try {

                wupin = "50700080:1:1,11010004:1:1,11000004:1:1";

                urlOfTool = "http://oqs" + serverId
                        + ".gamebox.com/hoho_admin/interface/export/interface.php?method=send_classic&date="
                        + sDateTime + "&time=" + timestamp + "&flag=" + secret + "&userid=" + guid + "&sendtitle="
                        + URLEncoder.encode(sendtitle, "UTF-8") + "&body=" + URLEncoder.encode(body, "UTF-8")
                        + "&wupin=" + wupin;
                if (serverId > 200 && serverId < 300) {
                    urlOfTool = "http://edtoqs" + (serverId - 200)
                            + ".gamebox.com/hoho_admin/interface/export/interface.php?method=send_classic&date="
                            + sDateTime + "&time=" + timestamp + "&flag=" + secret + "&userid=" + guid + "&sendtitle="
                            + URLEncoder.encode(sendtitle, "UTF-8") + "&body=" + URLEncoder.encode(body, "UTF-8")
                            + "&wupin=" + wupin;
                }
                if (serverId > 300 && serverId < 400) {
                    urlOfTool = "http://edtoqs" + (serverId - 300)
                            + ".gamebox.com/hoho_admin/interface/export/interface.php?method=send_classic&date="
                            + sDateTime + "&time=" + timestamp + "&flag=" + secret + "&userid=" + guid + "&sendtitle="
                            + URLEncoder.encode(sendtitle, "UTF-8") + "&body=" + URLEncoder.encode(body, "UTF-8")
                            + "&wupin=" + wupin;
                }
                WebUtils.sendGet(urlOfTool, null);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println(leaf);

        if (leaf == 200) {
            wupin = "21301005:1:1,50200024:1:1";
        }
        else if (leaf == 400) {
            wupin = "10500003:1:1,50700063:3:1,11010004:2:1";
        }
        else if (leaf == 600) {
            wupin = "50700063:3:1,10100053:10:1,10100032:2:1,10100047:1:1,50700080:2:1";
        }
        else if (leaf == 1200) {
            wupin = "50700070:1:1,10100053:18:1,10100032:7:1,10100048:1:1,50700080:4:1";
        }
        else if (leaf == 1800 || leaf == 2000) {
            wupin = "50700001:8:1,50700070:2:1,10100053:27:1,10100049:1:1,50700080:6:1";
        }
        else if (leaf == 4000) {
            wupin = "11000006:1:1,11010007:2:1,10100054:15:1,10100055:10:1,10100049:1:1,50700080:15:1";
        }
        else if (leaf == 6000) {
            wupin = "11000006:2:1,11010007:4:1,10100054:25:1,10600062:2:1,50700003:30:1,50700080:20:1";
        }
        else if (leaf == 10000) {
            wupin = "11000006:4:1,11010007:7:1,10100054:40:1,10600062:5:1,50700003:50:1,50700080:40:1";
        }
        else if (leaf == 20000) {
            wupin = "11000007:3:1,11010009:1:1,10100007:5:1,10100055:99:1,50700080:99:1,10600062:10:1";
        }
        else {
            return;
        }
       
            urlOfTool = "http://oqs" + serverId
                    + ".gamebox.com/hoho_admin/interface/export/interface.php?method=send_classic&date=" + sDateTime
                    + "&time=" + timestamp + "&flag=" + secret + "&userid=" + guid + "&sendtitle="
                    + URLEncoder.encode(sendtitle, "UTF-8") + "&body=" + URLEncoder.encode(body, "UTF-8") + "&wupin="
                    + wupin;
            if (serverId > 200 && serverId < 300) {
                urlOfTool = "http://edtoqs" + serverId
                        + ".gamebox.com/hoho_admin/interface/export/interface.php?method=send_classic&date="
                        + sDateTime + "&time=" + timestamp + "&flag=" + secret + "&userid=" + guid + "&sendtitle="
                        + URLEncoder.encode(sendtitle, "UTF-8") + "&body=" + URLEncoder.encode(body, "UTF-8")
                        + "&wupin=" + wupin;
            }
            if (serverId > 300 && serverId < 400) {
                urlOfTool = "http://edtoqs" + serverId
                        + ".gamebox.com/hoho_admin/interface/export/interface.php?method=send_classic&date="
                        + sDateTime + "&time=" + timestamp + "&flag=" + secret + "&userid=" + guid + "&sendtitle="
                        + URLEncoder.encode(sendtitle, "UTF-8") + "&body=" + URLEncoder.encode(body, "UTF-8")
                        + "&wupin=" + wupin;
            }
            WebUtils.sendGet(urlOfTool, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     * @see com.gamebox.service.ServerService#getSeversByGameId(java.lang.Integer)
     */
    @Override
    public List<Server> getSeversByGameId(Integer gameId) {

        List<Webgame> games = null;
        List<Server> servers = null;
       

//        List<Filter> filters = new ArrayList<Filter>(1);
//        Filter filter = new Filter();
//        filter.setOperator(Operator.eq);
//        filter.setProperty("gameId");
//        filter.setValue(gameId);
//        filters.add(filter);
//
//        List<Order> orders = new ArrayList<Order>(1);
//        Order gameOrder = new Order();
//        gameOrder.setDirection(Direction.asc);
//        gameOrder.setProperty("gameId");
//
//        Order serverOrder = new Order();
//        serverOrder.setDirection(Direction.asc);
//        serverOrder.setProperty("serverId");
//
//        orders.add(gameOrder);
//        orders.add(serverOrder);
//        if (null == gameId) {
//            games = gameService.findAll();
//            servers = this.findList(null, null, orders);
//            fixGamename2Server(games, servers);
//            return servers;
//        }
//        servers = this.findList(null, filters, orders);
//        Webgame game = gameService.findByGameId(gameId);
//        for (Server server : servers) {
//            server.setGameName(game.getName());
//        }
        return servers;
    }
    
    public Integer getNewestServerId(Integer gameId){
        return serverDao.getNewestServerId(gameId);
    }

    /**
     * @param games
     * @param servers
     */
    private void fixGamename2Server(List<Webgame> games, List<Server> servers) {

        for (Server server : servers) {
            for (Webgame game : games) {
                if (server.getGameId().equals(game.getGameId())) {
                    server.setGameName(game.getName());
                }
            }
        }
    }

}
