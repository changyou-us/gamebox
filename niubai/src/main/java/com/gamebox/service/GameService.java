package com.gamebox.service;

import java.util.List;

import com.gamebox.model.Users;
import com.gamebox.model.Webgame;
import com.gamebox.model.Webgame.OpenStatusType;


/**
 * Service - 管理员
 * 
 * @author Dick niu
 * @version 1.0
 */
public interface GameService{

    /**
     * 通过GameId查找Webgame
     * 
     * @param gameId
     *            游戏ID
     * @return 游戏
     */
    public Webgame findByGameId(Integer gid);

    /**
     * 通过GameName查找Webgame
     * 
     * @param name
     *            游戏名称
     * @return 游戏
     */
    public Webgame findByGameName(String name);

    /**
     * 通过GameIdentifer查找webgame
     * 
     * @param identifer
     *            唯一标识
     * @return 游戏
     */
    public Webgame findByGameIdentifier(String identifier);

    /**
     * 获取游戏
     * 
     * @return 返回所有游戏
     */
    public List<Webgame> getGames(OpenStatusType... openStatus);

    /**
     * 获取游戏
     * 
     * @param groupId
     * @return 返回所有游戏
     */
    public List<Webgame> getGames(Long groupId);

    /**
     * 获取排行榜地址
     * 
     * @param gameId
     *            游戏ID
     * @param serverId
     *            服务器ID
     * 
     * @return 排行榜URL
     */
    public String buildRankUrl(Integer gameId, String serverId);

    /**
     * 根据webgame的gameOwner获取玩家ID
     * 
     * @param gameId
     *            游戏ID
     * @param user
     *            用户
     * @return 玩家ID
     */
    public String getPlayerId(Integer gameId, Users user);

    /**
     * 该游戏是否在权限范围内
     * 
     * @param gameId
     * @return
     */
    public boolean isPermittedGame(Integer gameId);

    /**
     * 获取该用户权限所拥有的游戏集合
     * 
     * @return
     */
    public List<Webgame> getPermittedGames();

}