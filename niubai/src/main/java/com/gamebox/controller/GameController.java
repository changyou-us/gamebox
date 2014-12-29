package com.gamebox.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gamebox.service.GameService;


/**
 * Controller - 管理员
 * 
 * @author niuhongliang_tmp
 * @version 1.0
 */
@Controller("webgameGameController")
@RequestMapping("/webgame/play")
public class GameController {

    public static final String LOGIN_RESULT_SUCCESS = "success";

    public static final String LOGIN_PARAM_FAILURE = "param_failure";

    public static final String LOGIN_SERVER_FAILURE = "server_failure";

    public static final String LOGIN_URL_FAILURE = "url_failure";

    public static final String LOGIN_MAINTAIN = "maintain";

    @Resource(name = "gameServiceImpl")
    private GameService gameService;

    @Resource(name = "serverServiceImpl")
    private ServerService serverService;

    @Resource(name = "loginGameHistoryServiceImpl")
    private LoginGameHistoryService loginGameHistoryService;

    @Resource(name = "userPlayInfoServiceImpl")
    private UserPlayInfoService userPlayInfoService;

    @RequestMapping(value = "/login_game", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> loginGame(String gameId, String serverId, HttpServletRequest request,
            HttpServletResponse response) {

        Map<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isBlank(gameId) || StringUtils.isBlank(serverId)
                || !(ValidateUtils.isPositiveInt(gameId) && ValidateUtils.isPositiveInt(serverId))) {
            resultMap.put("result", LOGIN_PARAM_FAILURE);
            return resultMap;
        }
        Integer sid = null;
        Integer gid = Integer.parseInt(gameId);
        if ("10000000".equals(serverId)) {
            sid = serverService.getNewestServerId(gid);
        }
        else {
            sid = Integer.parseInt(serverId);
        }
        if (!serverService.serverExists(sid, gid, OpenStatusType.able, DisplayType.display)) {
            resultMap.put("result", LOGIN_SERVER_FAILURE);
            return resultMap;
        }
        Server stmp = serverService.findServerByGidAndSid(gid, sid);
        if (stmp.getStatus() != null && "maintain".equals(stmp.getStatus().name())) {
            resultMap.put("result", LOGIN_MAINTAIN);
            return resultMap;
        }
        Users user = (Users) request.getSession().getAttribute("user");
        String url = serverService.buildGameUrl(gid, sid, user);
        if (StringUtils.isBlank(url)) {
            resultMap.put("result", LOGIN_URL_FAILURE);
            return resultMap;
        }
        resultMap.put("result", LOGIN_RESULT_SUCCESS);
        if (request.getParameter("fb") != null) {
            url = url.replace("http:", "https:");
        }
        resultMap.put("url", url);
        resultMap.put("name", serverService.findServerByGidAndSid(gid, sid).getName());
        resultMap.put("timezone", serverService.findServerByGidAndSid(gid, sid).getTimezone().name());
        WebUtils.addCookie(request, response, "latestGame_" + gameId + "_" + user.getUserId(), serverId, SettingUtils
                .get().getCookieAge());
        // 向loginGameHistory表中插入数据,向userPlayInfo表中插入数据
        userPlayInfoService.save(gid, sid, user, request);

        // 更新session中user的玩游戏的信息
        Webgame game = gameService.findByGameId(gid);
        if (null != game) {
            user.setLastGameName(game.getName());
            // 获取游戏url
            user.setGameUrl(game.getHomepage());
            user.setGameLogoUrl(game.getLogoUrl());
            request.getSession().setAttribute(WebUtils.USER, user);
        }
        return resultMap;
    }

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public String play(String gameId, String gameUrl, ModelMap model) {

        try {
            model.addAttribute("gameUrl", URLDecoder.decode(gameUrl, "UTF-8"));
            model.addAttribute("webgame", gameService.findByGameId(Integer.parseInt(gameId)));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "/webgame/games/play";
    }
}
