package com.gamebox.service;

import java.util.List;

import com.gamebox.model.DirectPaymentOrder;
import com.gamebox.model.Server;
import com.gamebox.model.DisplayType;
import com.gamebox.model.Users;
import com.gamebox.model.OpenStatusType;

/**
 * Service - 管理员
 * 
 * @author Dick niu
 * @version 1.0
 */
public interface ServerService {


    /**
     * 判断服务器是否存在
     * 
     * @param serverId
     *            服务器ID
     * @param gameId
     *            游戏ID
     * @param openStatus
     *            游戏是否可用
     * @param display
     *            服务器是否展示
     * @return 服务器是否存在
     */
    boolean serverExists(Integer serverId, Integer gameId, OpenStatusType openStatus, DisplayType display);

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
     * 获取游戏登录地址
     * 
     * @param gameId
     *            游戏ID
     * @param serverId
     *            服务器ID
     * @param user
     *            用户信息
     * @return 登录URL
     */
    public String buildGameUrl(Integer gameId, Integer serverId, Users user);

    /**
     * 获取游戏排行榜地址
     * 
     * @param gameId
     *            游戏ID
     * @param serverId
     *            服务器ID
     * @param type
     *            排行榜类型
     * @return 排行榜URL
     */
    public String buildRankUrl(Integer gameId, Integer serverId, String type);

    /**
     * 游戏充值
     * 
     * @param gameId
     *            游戏ID
     * @param serverId
     *            服务器ID
     * @param userId
     *            用户id
     * @param amount
     *            充值金额
     * @param role
     *            角色
     * @param gameCoin
     *            传入的游戏币没有可以不写
     * @param paymentName
     *            传入的渠道名称
     * @param currency
     *            传入货币万一用得上
     * @param isValidate
     * 
     * @return 返回是否成功1为成功
     */
    public int recharge(Integer gameId, Integer serverId, Integer userId, String amount, String ordersn, String roleId, String roleName, Integer gameCoins, Integer gameCoin, Integer bonus,
            String paymentName, String currency, boolean isValidate, String description);

    int gamesCharge(DirectPaymentOrder directPaymentOrder);

    public Integer getNewestServerId(Integer gameId);
}
