package com.gamebox.dao;

import java.util.List;

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
public interface ServerDao {
    

    /**
     * 通过服务器ID和游戏ID查找
     * 
     * @param gid
     *            游戏ID
     * @param sid
     *            服务器ID
     * @return 服务器对象
     */
    public Server findServerByGidAndSid(Integer gid, Integer sid);

    /**
     * 通过游戏ID查找
     * 
     * @param gid
     *            游戏ID
     * @param invalid
     *            服务器状态：true-可用, false-不可用
     * @return 服务器对象数组
     */
    public List<Server> findByGameId(Integer gid, Boolean invalid);

    /**
     * 判断游戏服务器是否存在
     * 
     * @param sid
     *            服务器ID
     * @param gid
     *            游戏ID
     * @return 是否存在
     */
    public boolean serverExists(Integer sid, Integer gid);
    
    /**
     * 判断游戏服务器是否存在
     * 
     * @param sid
     *            服务器ID
     * @param gid
     *            游戏ID
     * @return 是否存在
     */
    public boolean serverExists(Integer sid, Integer gid, OpenStatusType openStatus, DisplayType display);

    /**
     * 更新IsNew字段
     * 
     * @param serverId
     *            服务器ID
     * @param gameId
     *            游戏ID
     * @param pValue
     *            要修改的字段值
     */
    boolean updateIsNew(Integer serverId, Integer gameId, IsNewType pValue);

    /**
     * 更新Recommended字段
     * 
     * @param serverId
     *            服务器ID
     * @param gameId
     *            游戏ID
     * @param pValue
     *            要修改的字段值
     */
    boolean updateRecommended(Integer serverId, Integer gameId, RecommendedType pValue);
    
    int updateStatus(Long[] ids, Integer status);
    
    Integer getNewestServerId(Integer gameId);
}
