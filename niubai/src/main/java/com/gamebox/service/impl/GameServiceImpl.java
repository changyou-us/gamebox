package com.gamebox.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamebox.dao.GameDao;
import com.gamebox.model.Users;
import com.gamebox.model.Webgame;
import com.gamebox.model.Webgame.OpenStatusType;
import com.gamebox.service.GameService;
import com.gamebox.util.Md5Token;

import freemarker.template.TemplateException;

/**
 * Service - 管理员
 * 
 * @author Dick niu
 * @version 1.0
 */
@Service("gameServiceImpl")
public class GameServiceImpl implements GameService {

    // 占位符，替换成serverId
    private static final String LOGIN_GAME_SERVERID_STRING = "$(sid)";

    // 占位符，替换成publicKey
    private static final String LOGIN_GAME_RANKKEY = "$(key)";

    // 占位符，对loginSecret进行32位加密
    private static final String LOGIN_GAME_OAUTH = "$(oauth)";

    @Resource(name = "gameDaoImpl")
    private GameDao gameDao;

    @Resource(name = "serverDaoImpl")
    private ServerDao serverDao;

    @Resource(name = "templateServiceImpl")
    private TemplateService templateService;

    @Transactional(readOnly = true)
    public Webgame findByGameId(Integer gameId) {

        return gameDao.findByGameId(gameId);
    }

    @Transactional(readOnly = true)
    public Webgame findByGameName(String name) {

        return gameDao.findByGameName(name);
    }

    @Transactional(readOnly = true)
    public Webgame findByGameIdentifier(String identifier) {

        return gameDao.findByGameIdentifier(identifier);
    }

    @Transactional(readOnly = true)
    public String buildGameUrl(Map<String, ?> map) {

        return null;
    }

    @Transactional(readOnly = true)
    public List<Webgame> getGames(OpenStatusType... openStatus) {

        Order order = new Order("id", Direction.asc);
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        if (openStatus.length > 0 && openStatus[0] == OpenStatusType.able) {
            Filter filter = new Filter("openStatus", Operator.eq, 1);
            List<Filter> filters = new ArrayList<Filter>();
            filters.add(filter);
            return findList(null, filters, orders);
        }
        return findList(null, null, orders);
    }

    @Transactional(readOnly = true)
    public String getPlayerId(Integer gameId, Users user) {

        Webgame webGame = findByGameId(gameId);
        switch (webGame.getGameOwner()) {
        case newGameBox:
            return user.getUserId().toString();
        case gameBox:
            return user.getGameboxUid() != null ? user.getGameboxUid().toString() : user.getUserId().toString();
        case gameFuse:
            return user.getGamefuseUid() != null ? user.getGamefuseUid().toString() : user.getUserId().toString();
        case tagame:
            return user.getTagameUid() != null ? user.getTagameUid().toString() : user.getUserId().toString();
        default:
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Webgame> getGames(Long groupId) {

        return gameDao.findByGroupId(groupId);
    }

    @Transactional(readOnly = true)
    public boolean isPermittedGame(Integer gameId) {

        Webgame webgame = this.findByGameId(gameId);
        if (webgame == null) {
            return false;
        }
        Subject currentAdmin = SecurityUtils.getSubject();
        return currentAdmin.isPermitted("admin:game_" + webgame.getGameId());
    }

    @Transactional(readOnly = true)
    public List<Webgame> getPermittedGames() {

        List<Webgame> permittedGames = new ArrayList<Webgame>();
        Subject currentAdmin = SecurityUtils.getSubject();
        List<Webgame> webgames = this.findAll();
        for (Webgame g : webgames) {
            if (currentAdmin.isPermitted("admin:game_" + g.getGameId())) {
                permittedGames.add(g);
            }
        }
        return permittedGames;
    }

    public String buildRankUrl(Integer gameId, String serverId) {

        Webgame game = gameDao.findByGameId(gameId);
        String oauth = "";
        // 1.解析loginSecret
        String rankSecret = game.getRankSecret();
        rankSecret = replaceUrl(rankSecret, serverId, game.getRankKey(), oauth);
        oauth = Md5Token.getInstance().getLongToken(rankSecret);
        return replaceUrl(game.getRankUrl(), serverId, game.getRankKey(), oauth);
    }

    private String replaceUrl(String originalUrl, String serverId, String key, String oauth) {

        // 1. 将字符串进行初步直接替换
        String url = originalUrl.replace(LOGIN_GAME_SERVERID_STRING, serverId).replace(LOGIN_GAME_RANKKEY, key)
                .replace(LOGIN_GAME_OAUTH, oauth);

        return url;
    }

    public String getStaticLink(String name) {

        Template template = templateService.get("gameIndex");
        Map<String, Object> tModel = new HashMap<String, Object>();
        tModel.put("name", name);
        try {
            return FreemarkerUtils.process(template.getStaticPath(), tModel);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (TemplateException e) {
            e.printStackTrace();
            return null;
        }

    }

}
