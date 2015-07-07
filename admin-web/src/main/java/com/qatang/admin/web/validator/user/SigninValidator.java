package com.qatang.admin.web.validator.user;

import com.qatang.admin.web.form.user.UserForm;
import com.qatang.core.exception.ValidateFailedException;
import com.qatang.core.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author qatang
 * @since 2015-01-27 10:02
 */
@Component
public class SigninValidator extends AbstractValidator<UserForm> {
    private String captchaExpected;

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

        if (StringUtils.isEmpty(userForm.getUser().getPassword())) {
            String msg = String.format("密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }

        if (StringUtils.isEmpty(userForm.getCaptcha())) {
            String msg = String.format("验证码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("captcha", msg);
        }

        if (userForm.getUser().getPassword().length() < 6 || userForm.getUser().getPassword().length() > 16) {
            String msg = String.format("密码长度必须在5-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("user.password", msg);
        }
        if (userForm.getCaptcha().length() != 5) {
            String msg = String.format("验证码长度必须是5个字符");
            logger.error(msg);
            throw new ValidateFailedException("captcha", msg);
        }

        if (!userForm.getCaptcha().equals(captchaExpected)) {
            String msg = String.format("验证码不匹配");
            logger.error(msg);
            throw new ValidateFailedException("captcha", msg);
        }

        return true;
    }

    public void setCaptchaExpected(String captchaExpected) {
        this.captchaExpected = captchaExpected;
    }
}
