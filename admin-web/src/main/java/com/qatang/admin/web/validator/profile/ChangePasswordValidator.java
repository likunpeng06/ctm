package com.qatang.admin.web.validator.profile;

import com.qatang.admin.entity.user.User;
import com.qatang.admin.service.user.UserService;
import com.qatang.admin.web.form.user.UserForm;
import com.qatang.admin.web.shiro.authentication.PasswordHelper;
import com.qatang.core.exception.ValidateFailedException;
import com.qatang.core.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qatang
 * @since 2015-01-27 10:02
 */
@Component
public class ChangePasswordValidator extends AbstractValidator<UserForm> {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public boolean validate(UserForm userForm) throws ValidateFailedException {
        logger.info("开始验证userForm参数");
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        if (currentUser == null) {
            String msg = String.format("currentUser不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userForm", msg);
        }
        currentUser = userService.get(currentUser.getId());
        if (currentUser == null) {
            String msg = String.format("currentUser不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userForm", msg);
        }
        if (userForm == null || userForm.getUser() == null) {
            String msg = String.format("userForm参数不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userForm", msg);
        }
        if (StringUtils.isEmpty(userForm.getUser().getPassword())) {
            String msg = String.format("旧密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        if (userForm.getUser().getPassword().length() < 6 || userForm.getUser().getPassword().length() > 16) {
            String msg = String.format("旧密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        if (StringUtils.isEmpty(userForm.getNewPassword())) {
            String msg = String.format("新密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }
        if (userForm.getNewPassword().length() < 6 || userForm.getNewPassword().length() > 16) {
            String msg = String.format("新密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }
        if (StringUtils.isEmpty(userForm.getConPassword())) {
            String msg = String.format("确认密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("conPassword", msg);
        }
        if (userForm.getConPassword().length() < 6 || userForm.getConPassword().length() > 16) {
            String msg = String.format("确认密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("conPassword", msg);
        }
        if (!userForm.getNewPassword().equals(userForm.getConPassword())) {
            String msg = String.format("两次输入密码不一致");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }

        if (!passwordHelper.validPassword(currentUser, userForm.getUser().getPassword())) {
            String msg = String.format("旧密码错误");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }

        if (passwordHelper.validPassword(currentUser, userForm.getNewPassword())) {
            String msg = String.format("新密码不能和旧密码一样");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        return true;
    }
}
