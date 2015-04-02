package com.gamebox.service;

import com.gamebox.model.Users;
import com.gamebox.model.Webgame;


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


}