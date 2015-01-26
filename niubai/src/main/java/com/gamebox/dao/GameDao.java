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

}
