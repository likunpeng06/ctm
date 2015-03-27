package com.qatang.admin.service.game.impl;

import com.qatang.admin.dao.game.UploadFileDao;
import com.qatang.admin.entity.game.UploadFile;
import com.qatang.admin.service.game.UploadFileService;
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
public class UploadFileServiceImpl extends AbstractService<UploadFile, Long> implements UploadFileService {
    @Autowired
    private UploadFileDao uploadfileDao;

}
