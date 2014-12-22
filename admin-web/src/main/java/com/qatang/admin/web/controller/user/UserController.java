package com.qatang.admin.web.controller.user;

import com.qatang.admin.entity.user.User;
import com.qatang.admin.service.user.UserService;
import com.qatang.core.controller.BaseController;
import com.qatang.core.enums.EnableDisableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * @author qatang
 * @since 2014-12-20 13:41
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

//    @RequiresPermissions("sys:user:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap modelMap) {
        Page<User> page = userService.findAll(null);
        modelMap.addAttribute("userList", page.getContent());
        return "user/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String input(@ModelAttribute User user, ModelMap modelMap) {
        modelMap.addAttribute("enableDisableStatusList", EnableDisableStatus.list());
        return "user/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            modelMap.addAttribute(ERROR_MESSAGE_KEY, result.getFieldError().toString());
            return "user/create";
        }

        //conPassword验证

        //多语言支持

        //salt
        user.setSalt("1111111111111111");

        userService.save(user);

        modelMap.addAttribute(SUCCESS_MESSAGE_KEY, "用户创建成功！");
        return "success";
    }
}
