package com.qatang.admin.web.controller.game;

import com.qatang.admin.entity.game.Game;
import com.qatang.admin.query.game.GameSearchable;
import com.qatang.admin.service.game.GameService;
import com.qatang.admin.web.form.game.GameForm;
import com.qatang.admin.web.validator.game.CreateGameValidator;
import com.qatang.admin.web.validator.game.UpdateGameValidator;
import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.controller.BaseController;
import com.qatang.core.exception.ValidateFailedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author qatang
 * @since 2014-12-20 13:41
 */
@Controller
@RequestMapping("/game")
@SessionAttributes("gameSearchable")
public class GameController extends BaseController {
    @Autowired
    private GameService gameService;

    @Autowired
    private CreateGameValidator createGameValidator;
    @Autowired
    private UpdateGameValidator updateGameValidator;

    @ModelAttribute("orderFieldMap")
    public Map<String, String> getOrderFieldList() {
        Map<String, String> orderFieldMap = new LinkedHashMap<>();
        orderFieldMap.put("id,desc", "编码从大到小");
        orderFieldMap.put("id,asc", "编码从小到大");
        orderFieldMap.put("createdTime,desc", "创建时间从晚到早");
        orderFieldMap.put("createdTime,asc", "创建时间从早到晚");
        return orderFieldMap;
    }


    @RequiresPermissions("operation:game:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(GameSearchable gameSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        gameSearchable = new GameSearchable();
        gameSearchable.setPageable(pageable);

        Page<Game> page = gameService.find(gameSearchable);

        modelMap.addAttribute("page", page);
        return "game/list";
    }

    @RequiresPermissions("operation:game:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String search(GameSearchable gameSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap, HttpServletRequest request) {
        gameSearchable.setPageable(pageable);

        Page<Game> page = gameService.find(gameSearchable);

        modelMap.addAttribute("page", page);
        return "game/list";
    }

    @RequiresPermissions("operation:game:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createInput(@ModelAttribute GameForm gameForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".gameForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        modelMap.addAttribute(FORWARD_URL_KEY, "/game/list");
        return "game/create";
    }

    @RequiresPermissions("operation:game:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(GameForm gameForm, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            createGameValidator.validate(gameForm);
        } catch (ValidateFailedException e) {
            logger.error(e.getMessage(), e);
            result.addError(new ObjectError(e.getField(), e.getMessage()));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(gameForm);
            return "redirect:/game/create";
        }

        Game game = gameForm.getGame();
        gameService.save(game);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
        return "redirect:/success";
    }

    @RequiresPermissions("operation:game:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateInput(@PathVariable String id, @ModelAttribute GameForm gameForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("修改游戏错误：game.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }
        Long gameId = null;
        try {
            gameId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("修改游戏错误：game.id不能转换成Long类型！game.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (gameId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }

        Game game = gameService.get(gameId);
        if (game == null) {
            logger.error("修改游戏错误：未查询到game.id={}的游戏！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".gameForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        gameForm.setGame(game);
        modelMap.addAttribute(FORWARD_URL_KEY, "/game/list");
        return "game/update";
    }

    @RequiresPermissions("operation:game:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(GameForm gameForm, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            updateGameValidator.validate(gameForm);
        } catch (ValidateFailedException e) {
            logger.error(e.getMessage(), e);
            result.addError(new ObjectError(e.getField(), e.getMessage()));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(gameForm);
            return "redirect:/game/update/" + gameForm.getGame().getId();
        }

        Game game = gameForm.getGame();

        Game updateGame = gameService.get(game.getId());
        updateGame.setName(game.getName());
        updateGame.setIconUrl(game.getIconUrl());
        updateGame.setLargeImgUrl(game.getLargeImgUrl());
        updateGame.setGameUrl(game.getGameUrl());
        updateGame.setDescription(game.getDescription());
        updateGame.setUpdatedTime(new Date());
        gameService.update(updateGame);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
        return "redirect:/success";
    }

    @RequiresPermissions("operation:game:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("查看游戏错误：game.id对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }
        Long gameId = null;
        try {
            gameId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("查看游戏错误：game.id不能转换成Long类型！game.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (gameId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }

        Game game = gameService.get(gameId);
        if (game == null) {
            logger.error("查询游戏错误：未查询到game.id={}的游戏！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }

        GameForm gameForm = new GameForm();
        gameForm.setGame(game);
        modelMap.addAttribute(gameForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/game/list");
        return "game/detail";
    }

    @RequiresPermissions("operation:game:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(id)) {
            logger.error("删除游戏错误：game.id对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }
        Long gameId = null;
        try {
            gameId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("删除游戏错误：game.id不能转换成Long类型！game.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (gameId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }

        Game game = gameService.get(gameId);
        if (game == null) {
            logger.error("删除游戏错误：未查询到game.id={}的游戏！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/game/list");
            return "redirect:/error";
        }

        gameService.delete(game.getId());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        return "redirect:/game/list";
    }

}
