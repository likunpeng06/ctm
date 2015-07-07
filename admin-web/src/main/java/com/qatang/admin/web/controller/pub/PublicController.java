package com.qatang.admin.web.controller.pub;

import com.alibaba.fastjson.JSON;
import com.qatang.admin.entity.game.Game;
import com.qatang.admin.service.game.GameService;
import com.qatang.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qatang
 * @since 2014-12-20 13:41
 */
@Controller
@RequestMapping("/api")
public class PublicController extends BaseController {
    @Autowired
    private GameService gameService;


    @RequestMapping(value = "/game/list", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getGameList() {
        List<Game> games = gameService.findAll();

        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("count", games.size());

        List<Map<String, Object>> gamesJsonList = new ArrayList<>();
        for (Game game : games) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", game.getId());
            map.put("name", game.getName());
            map.put("icon_url", game.getIconUrl());
            map.put("large_img_url", game.getLargeImgUrl());
            map.put("game_url", game.getGameUrl());
            map.put("description", game.getDescription());

            gamesJsonList.add(map);
        }
        rootMap.put("games", gamesJsonList);
        return JSON.toJSONString(rootMap);
    }
}
