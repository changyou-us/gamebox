package com.gamebox.dao;

import java.util.List;

import com.gamebox.model.Webgame;


/**
 * Dao - 管理员
 * 
 * @author 
 * @version 1.0
 */
public interface GameDao{

    /**
     * 通过GameId查找Webgame
     * 
     * @param gameId
     *            游戏ID
     * @return 游戏
     */
    public Webgame findByGameId(Integer gameId);

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
     * 通过GroupId查找Webgame
     * 
     * @param groupId
     *            小组ID
     * @return 游戏
     */
    public List<Webgame> findByGroupId(Long groupId);
}
