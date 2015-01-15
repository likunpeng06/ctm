package com.qatang.admin.web.controller.user;

import com.qatang.admin.entity.user.User;
import com.qatang.admin.query.user.UserSearchable;
import com.qatang.admin.service.user.UserService;
import com.qatang.admin.web.form.user.UserForm;
import com.qatang.admin.web.shiro.authentication.PasswordHelper;
import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.controller.BaseController;
import com.qatang.core.enums.EnableDisableStatus;
import com.qatang.core.enums.OrderDirection;
import com.qatang.core.enums.YesNoStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qatang
 * @since 2014-12-20 13:41
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("userSearchable")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;

    @ModelAttribute("orderDirectionList")
    public List<OrderDirection> getOrderDirectionList() {
        return OrderDirection.list();
    }

    @ModelAttribute("orderFieldMap")
    public Map<String, String> getOrderFieldList() {
        Map<String, String> orderFieldMap = new HashMap<>();
        orderFieldMap.put("id", "编码");
        orderFieldMap.put("createdTime", "创建时间");
        return orderFieldMap;
    }

    @ModelAttribute("enableDisableStatusList")
    public List<EnableDisableStatus> getEnableDisableStatusList() {
        return EnableDisableStatus.list();
    }

    @ModelAttribute("queryEnableDisableStatusList")
    public List<EnableDisableStatus> getQueryEnableDisableStatusList() {
        return EnableDisableStatus.listAll();
    }

//    @RequiresPermissions("sys:user:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(UserSearchable userSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        userSearchable = new UserSearchable();
        userSearchable.setPageable(pageable);

        Page<User> page = userService.findAll(userSearchable);

        modelMap.addAttribute("page", page);
        return "user/list";
    }

//    @RequiresPermissions("sys:user:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String search(UserSearchable userSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap, HttpServletRequest request) {
        userSearchable.setPageable(pageable);

        Page<User> page = userService.findAll(userSearchable);

        modelMap.addAttribute("page", page);
        return "user/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createInput(@ModelAttribute UserForm userForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        return "user/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid UserForm userForm, BindingResult result, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (userForm == null || userForm.getUser() == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.data}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        if (StringUtils.isEmpty(userForm.getUser().getUsername())) {
            result.addError(new ObjectError("user.username", "{not.null}"));
        }

        if (StringUtils.isEmpty(userForm.getUser().getEmail())) {
            result.addError(new ObjectError("user.email", "{not.null}"));
        }

        if (StringUtils.isEmpty(userForm.getUser().getPassword()) || StringUtils.isEmpty(userForm.getConPassword())) {
            result.addError(new ObjectError("user.password", "{not.null}"));
        }

        if (!userForm.getConPassword().equals(userForm.getUser().getPassword())) {
            result.addError(new ObjectError("user.password", "{password.fields.must.match}"));
        }

        User conUser = userService.findByUsername(userForm.getUser().getUsername());
        if (conUser != null) {
            result.addError(new ObjectError("user.username", "{username.has.been.registered}"));
        }

        conUser = userService.findByEmail(userForm.getUser().getEmail());
        if (conUser != null) {
            result.addError(new ObjectError("user.email", "{email.has.been.registered}"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/create";
        }

        //多语言支持

        User user = userForm.getUser();
        passwordHelper.encryptPassword(user);
        userService.save(user);

        modelMap.addAttribute(SUCCESS_MESSAGE_KEY, "{success}");
        modelMap.addAttribute(FORWARD_URL_KEY, "/user/list");
        return "success";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateInput(@PathVariable String id, @ModelAttribute UserForm userForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (userId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        User user = userService.get(userId);
        if (user == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        userForm.setUser(user);
        return "user/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (userForm == null || userForm.getUser() == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.data}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        if (userForm.getUser().getId() == null) {
            result.addError(new ObjectError("user.id", "{user.id.not.null}"));
        }

        if (userForm.getUser().getValid() == null) {
            result.addError(new ObjectError("user.valid", "{user.valid.not.null}"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/update/" + userForm.getUser().getId();
        }

        User user = userForm.getUser();

        User updateUser = userService.get(user.getId());
        updateUser.setMobile(user.getMobile());
        updateUser.setValid(user.getValid());
        updateUser.setUpdatedTime(new Date());
        userService.update(updateUser);

        modelMap.addAttribute(SUCCESS_MESSAGE_KEY, "{success}");
        modelMap.addAttribute(FORWARD_URL_KEY, "/user/list");
        return "success";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (userId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        User user = userService.get(userId);
        if (user == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        UserForm userForm = new UserForm();
        userForm.setUser(user);
        modelMap.addAttribute(userForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/user/list");
        return "user/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (userId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        User user = userService.get(userId);
        if (user == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        if (user.getRoot() == YesNoStatus.YES) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{delete.invalid}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        userService.delete(user.getId());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "{delete.success}");
        return "redirect:/user/list";
    }
}
