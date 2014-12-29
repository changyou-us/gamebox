package com.gamebox.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gamebox.dao.GameDao;
import com.gamebox.model.Webgame;

/**
 * Dao - 管理员
 * 
 * @author Dick Niu
 * @version 1.0
 */
@Repository("gameDaoImpl")
public class GameDaoImpl implements GameDao {

    public Webgame findByGameId(Integer gid) {
        return null;

    }

    public Webgame findByGameName(String name) {
        return null;

    }

    public Webgame findByGameIdentifier(String identifier) {
        return null;

    }

    @Override
    public List<Webgame> findByGroupId(Long groupId) {
        return null;

    }

}
