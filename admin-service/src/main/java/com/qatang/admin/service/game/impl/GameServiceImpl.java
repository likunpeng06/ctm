package com.qatang.admin.service.game.impl;

import com.qatang.admin.dao.game.GameDao;
import com.qatang.admin.entity.game.Game;
import com.qatang.admin.service.game.GameService;
import com.qatang.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author qatang
 * @since 2014-12-19 14:53
 */
@Service
@Transactional
public class GameServiceImpl extends AbstractService<Game, Long> implements GameService {
    @Autowired
    private GameDao gameDao;

}
