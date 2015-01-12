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
import com.gamebox.dao.ServerDao;
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

        return null;
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

        return null;
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


}
