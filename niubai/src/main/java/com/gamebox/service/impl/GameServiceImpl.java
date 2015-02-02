package com.gamebox.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamebox.dao.GameDao;
import com.gamebox.dao.ServerDao;
import com.gamebox.model.Users;
import com.gamebox.model.Webgame;
import com.gamebox.service.GameService;
import com.gamebox.util.Md5Token;

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

    @Autowired
    private GameDao gameDao;

    @Autowired
    private ServerDao serverDao;

    public Webgame findByGameId(Integer gameId) {

        return gameDao.findByGameId(gameId);
    }

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
