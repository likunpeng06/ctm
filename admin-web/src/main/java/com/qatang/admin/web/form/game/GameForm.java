package com.qatang.admin.web.form.game;

import com.qatang.admin.entity.game.Game;
import com.qatang.core.form.AbstractForm;

/**
 * @author qatang
 * @since 2014-12-24 16:01
 */
public class GameForm extends AbstractForm {

    private static final long serialVersionUID = -6271449438256175735L;

    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
