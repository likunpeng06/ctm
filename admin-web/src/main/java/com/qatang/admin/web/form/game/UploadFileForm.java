package com.qatang.admin.web.form.game;

import com.qatang.admin.entity.game.Game;
import com.qatang.admin.entity.game.UploadFile;
import com.qatang.core.form.AbstractForm;

/**
 * @author qatang
 * @since 2014-12-24 16:01
 */
public class UploadFileForm extends AbstractForm {


    private static final long serialVersionUID = 3437448757350545876L;

    private UploadFile uploadFile;

    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
