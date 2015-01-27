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
public class UpdateUserValidator extends AbstractValidator<UserForm> {
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
        if (userForm.getUser().getId() == null) {
            String msg = String.format("更新用户，用户id为空");
            logger.error(msg);
            throw new ValidateFailedException("user.id", msg);
        }

        User user = userService.get(userForm.getUser().getId());
        if (user == null) {
            String msg = String.format("所要更新的用户不存在");
            logger.error(msg);
            throw new ValidateFailedException("user.id", msg);
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
        User emailUser = userService.findByEmail(userForm.getUser().getEmail());
        if (emailUser != null && !user.getEmail().equals(userForm.getUser().getEmail())) {
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

        if (userForm.getUser().getEmailValid() == null) {
            String msg = String.format("是否验证邮箱不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.emailValid", msg);
        }

        if (userForm.getUser().getMobileValid() == null) {
            String msg = String.format("是否验证手机不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.mobileValid", msg);
        }

        return true;
    }
}
