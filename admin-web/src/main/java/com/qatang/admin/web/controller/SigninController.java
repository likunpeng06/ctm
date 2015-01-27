package com.qatang.admin.web.controller;


import com.qatang.admin.web.form.user.UserForm;
import com.qatang.admin.web.validator.user.SigninValidator;
import com.qatang.core.controller.BaseController;
import com.qatang.core.exception.ValidateFailedException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qatang on 14-6-5.
 */
@Controller
@SessionAttributes(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY)
public class SigninController extends BaseController {
    private static final String kaptchaSessionKey = com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;
    @Autowired
    private SigninValidator signinValidator;

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signinPage(@ModelAttribute UserForm userForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        return "user/signin";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signin(UserForm userForm, @ModelAttribute(kaptchaSessionKey) String captchaExpected, BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        signinValidator.setCaptchaExpected(captchaExpected);
        try {
            signinValidator.validate(userForm);
        } catch (ValidateFailedException e) {
            logger.error(e.getMessage(), e);
            result.addError(new ObjectError(e.getField(), e.getMessage()));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/signin";
        }

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userForm.getUser().getUsername(), userForm.getUser().getPassword());
        usernamePasswordToken.setRememberMe(userForm.isRememberMe());

        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            if (e instanceof IncorrectCredentialsException) {
                result.addError(new ObjectError("user.username", "用户名或密码不正确！"));
            } else {
                result.addError(new ObjectError("user.username", e.getMessage()));
            }
            logger.error(e.getMessage(), e);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/signin";
        }
        return "redirect:/dashboard";
    }
}
