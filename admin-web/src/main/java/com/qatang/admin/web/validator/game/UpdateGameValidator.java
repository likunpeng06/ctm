package com.qatang.admin.web.validator.game;

import com.qatang.admin.entity.game.Game;
import com.qatang.admin.service.game.GameService;
import com.qatang.admin.web.form.game.GameForm;
import com.qatang.core.exception.ValidateFailedException;
import com.qatang.core.utils.ValidatorUtils;
import com.qatang.core.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qatang
 * @since 2015-01-27 10:02
 */
@Component
public class UpdateGameValidator extends AbstractValidator<GameForm> {
    @Autowired
    private GameService gameService;

    @Override
    public boolean validate(GameForm gameForm) throws ValidateFailedException {
        logger.info("开始验证gameForm参数");
        if (gameForm == null || gameForm.getGame() == null) {
            String msg = String.format("gameForm参数不能为空");
            logger.error(msg);
            throw new ValidateFailedException("gameForm", msg);
        }
        if (gameForm.getGame().getId() == null) {
            String msg = String.format("更新游戏，游戏id为空");
            logger.error(msg);
            throw new ValidateFailedException("game.id", msg);
        }

        Game game = gameService.get(gameForm.getGame().getId());
        if (game == null) {
            String msg = String.format("所要更新的游戏不存在");
            logger.error(msg);
            throw new ValidateFailedException("game.id", msg);
        }

        if (StringUtils.isEmpty(gameForm.getGame().getName())) {
            String msg = String.format("游戏名不能为空");
            logger.error(msg);
            throw new ValidateFailedException("game.name", msg);
        }
        if (gameForm.getGame().getName().length() < 4 || gameForm.getGame().getName().length() > 64) {
            String msg = String.format("游戏名长度必须在4-64个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("game.name", msg);
        }

        return true;
    }
}
