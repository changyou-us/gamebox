package com.gamebox.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gamebox.dao.ServerDao;
import com.gamebox.model.Server;
import com.gamebox.model.Server.DisplayType;
import com.gamebox.model.Server.IsNewType;
import com.gamebox.model.Server.RecommendedType;
import com.gamebox.model.Webgame.OpenStatusType;

/**
 * Dao - 管理员
 * 
 * @author Dick Niu
 * @version 1.0
 */
@Repository("serverDaoImpl")
public class ServerDaoImpl implements ServerDao {

    public Server findServerByGidAndSid(Integer gid, Integer sid) {
        return null;

    }

    public List<Server> findByGameId(Integer gid, Boolean invalid) {
        return null;

    }

    public boolean serverExists(Integer sid, Integer gid) {
        return false;

    }

    public boolean serverExists(Integer sid, Integer gid, OpenStatusType openStatus, DisplayType display) {
        return false;

    }

    public boolean updateIsNew(Integer serverId, Integer gameId, IsNewType pValue) {
        return false;

    }

    public boolean updateRecommended(Integer serverId, Integer gameId, RecommendedType pValue) {
        return false;

    }

    public int updateStatus(Long[] ids, Integer status) {
        return status;

    }
    
    public Integer getNewestServerId(Integer gameId){
        return gameId;
    }
}
