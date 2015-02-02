package com.gamebox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gamebox.model.Server;
import com.gamebox.model.DisplayType;
import com.gamebox.model.OpenStatusType;


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
    public Server findServerByGidAndSid(@Param("gameId") Integer gid,@Param("serverId") Integer sid);

    /**
     * 通过游戏ID查找
     * 
     * @param gid
     *            游戏ID
     * @param invalid
     *            服务器状态：true-可用, false-不可用
     * @return 服务器对象数组
     */
    public List<Server> findByGameId(@Param("gameId") Integer gid,@Param("display") Boolean invalid);

    /**
     * 判断游戏服务器是否存在
     * 
     * @param sid
     *            服务器ID
     * @param gid
     *            游戏ID
     * @return 是否存在
     */
    
    /**
     * 判断游戏服务器是否存在
     * 
     * @param sid
     *            服务器ID
     * @param gid
     *            游戏ID
     * @return 是否存在
     */
    public boolean serverExists(@Param("serverId")Integer sid, @Param("gameId") Integer gid, @Param("openStatus") OpenStatusType openStatus, @Param("display") DisplayType display);
    
    public Integer getNewestServerId(@Param("gameId") Integer gameId);
}
