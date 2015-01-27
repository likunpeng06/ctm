package com.qatang.admin.web.validator.user;

import com.qatang.admin.entity.user.User;
import com.qatang.admin.service.user.UserService;
import com.qatang.admin.web.form.user.UserForm;
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
public class CreateUserValidator extends AbstractValidator<UserForm> {
    @Autowired
    private UserService userService;

    @Override
    public boolean validate(UserForm userForm) throws ValidateFailedException {
        logger.info("开始验证userForm参数");
        if (userForm == null || userForm.getUser() == null) {
            String msg = String.format("userForm参数不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userForm", msg);
        }
        if (StringUtils.isEmpty(userForm.getUser().getUsername())) {
            String msg = String.format("用户名不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.username", msg);
        }
        if (userForm.getUser().getUsername().length() < 4 || userForm.getUser().getUsername().length() > 20) {
            String msg = String.format("用户名长度必须在4-20个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("user.username", msg);
        }
        if (!ValidatorUtils.checkUsername(userForm.getUser().getUsername())) {
            String msg = String.format("用户名格式错误");
            logger.error(msg);
            throw new ValidateFailedException("user.username", msg);
        }
        User user = userService.findByUsername(userForm.getUser().getUsername());
        if (user != null) {
            String msg = String.format("用户名已存在");
            logger.error(msg);
            throw new ValidateFailedException("user.username", msg);
        }
        if (StringUtils.isEmpty(userForm.getUser().getPassword())) {
            String msg = String.format("密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        if (userForm.getUser().getPassword().length() < 6 || userForm.getUser().getPassword().length() > 16) {
            String msg = String.format("密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
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
        if (!userForm.getUser().getPassword().equals(userForm.getConPassword())) {
            String msg = String.format("两次输入密码不一致");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        if (StringUtils.isEmpty(userForm.getUser().getEmail())) {
            String msg = String.format("用户邮箱不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.email", msg);
        }
        if (!ValidatorUtils.checkEmail(userForm.getUser().getEmail())) {
            String msg = String.format("邮箱格式错误");
            logger.error(msg);
            throw new ValidateFailedException("user.email", msg);
        }
        //邮箱是否已存在验证
        user = userService.findByEmail(userForm.getUser().getEmail());
        if (user != null) {
            String msg = String.format("该邮箱已被使用，请更换其他邮箱");
            logger.error(msg);
            throw new ValidateFailedException("user.email", msg);
        }
        if (StringUtils.isNotEmpty(userForm.getUser().getMobile())) {
            if (!ValidatorUtils.checkMobile(userForm.getUser().getMobile())) {
                String msg = String.format("手机格式错误");
                logger.error(msg);
                throw new ValidateFailedException("user.mobile", msg);
            }
        }

        if (userForm.getUser().getValid() == null) {
            String msg = String.format("是否有效不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.valid", msg);
        }

        return true;
    }
}
