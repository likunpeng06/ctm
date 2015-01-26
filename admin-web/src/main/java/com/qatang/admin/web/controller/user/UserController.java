package com.qatang.admin.web.controller.user;

import com.qatang.admin.entity.role.Role;
import com.qatang.admin.entity.user.User;
import com.qatang.admin.query.user.UserSearchable;
import com.qatang.admin.service.role.RoleService;
import com.qatang.admin.service.user.UserService;
import com.qatang.admin.web.form.user.UserForm;
import com.qatang.admin.web.shiro.authentication.PasswordHelper;
import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.controller.BaseController;
import com.qatang.core.enums.EnableDisableStatus;
import com.qatang.core.enums.YesNoStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.util.*;

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
    private RoleService roleService;
    @Autowired
    private PasswordHelper passwordHelper;

    @ModelAttribute("orderFieldMap")
    public Map<String, String> getOrderFieldList() {
        Map<String, String> orderFieldMap = new LinkedHashMap<>();
        orderFieldMap.put("id,desc", "编码从大到小");
        orderFieldMap.put("id,asc", "编码从小到大");
        orderFieldMap.put("createdTime,desc", "创建时间从晚到早");
        orderFieldMap.put("createdTime,asc", "创建时间从早到晚");
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

    @RequiresPermissions("sys:user:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(UserSearchable userSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        userSearchable = new UserSearchable();
        userSearchable.setPageable(pageable);

        Page<User> page = userService.find(userSearchable);

        modelMap.addAttribute("page", page);
        return "user/list";
    }

    @RequiresPermissions("sys:user:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String search(UserSearchable userSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap, HttpServletRequest request) {
        userSearchable.setPageable(pageable);

        Page<User> page = userService.find(userSearchable);

        modelMap.addAttribute("page", page);
        return "user/list";
    }

    @RequiresPermissions("sys:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createInput(@ModelAttribute UserForm userForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        modelMap.addAttribute(FORWARD_URL_KEY, "/user/list");
        return "user/create";
    }

    @RequiresPermissions("sys:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid UserForm userForm, BindingResult result, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (userForm == null || userForm.getUser() == null) {
            logger.error("新建用户错误：userForm或者userForm.user对象为空");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        if (StringUtils.isEmpty(userForm.getUser().getUsername())) {
            result.addError(new ObjectError("user.username", "用户名不能为空！"));
        }

        if (StringUtils.isEmpty(userForm.getUser().getEmail())) {
            result.addError(new ObjectError("user.email", "邮箱不能为空！"));
        }

        if (StringUtils.isEmpty(userForm.getUser().getPassword()) || StringUtils.isEmpty(userForm.getConPassword())) {
            result.addError(new ObjectError("user.password", "密码不能为空！"));
        }

        if (!userForm.getConPassword().equals(userForm.getUser().getPassword())) {
            result.addError(new ObjectError("user.password", "密码与确认密码不匹配！"));
        }

        User conUser = userService.findByUsername(userForm.getUser().getUsername());
        if (conUser != null) {
            result.addError(new ObjectError("user.username", "用户名已经被注册！"));
        }

        conUser = userService.findByEmail(userForm.getUser().getEmail());
        if (conUser != null) {
            result.addError(new ObjectError("user.email", "邮箱已经被注册！"));
        }

        if (userForm.getUser().getValid() == null) {
            result.addError(new ObjectError("user.valid", "用户有效状态不能为空！"));
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

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
        return "redirect:/success";
    }

    @RequiresPermissions("sys:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateInput(@PathVariable String id, @ModelAttribute UserForm userForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("修改用户错误：user.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("修改用户错误：user.id不能转换成Long类型！user.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (userId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        User user = userService.get(userId);
        if (user == null) {
            logger.error("修改用户错误：未查询到user.id={}的用户！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        userForm.setUser(user);
        modelMap.addAttribute(FORWARD_URL_KEY, "/user/list");
        return "user/update";
    }

    @RequiresPermissions("sys:user:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (userForm == null || userForm.getUser() == null) {
            logger.error("修改用户错误：userForm或者userForm.user对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
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

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
        return "redirect:/success";
    }

    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("查看用户错误：user.id对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("查看用户错误：user.id不能转换成Long类型！user.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (userId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        User user = userService.get(userId);
        if (user == null) {
            logger.error("查询用户错误：未查询到user.id={}的用户！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        UserForm userForm = new UserForm();
        userForm.setUser(user);
        modelMap.addAttribute(userForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/user/list");
        return "user/detail";
    }

    @RequiresPermissions("sys:user:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(id)) {
            logger.error("删除用户错误：user.id对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("删除用户错误：user.id不能转换成Long类型！user.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (userId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        User user = userService.get(userId);
        if (user == null) {
            logger.error("删除用户错误：未查询到user.id={}的用户！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        if (user.getRoot() == YesNoStatus.YES) {
            logger.error("删除用户错误：root用户无法删除！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "root用户无法删除！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/user/list";
        }

        userService.delete(user.getId());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        return "redirect:/user/list";
    }

    @RequiresPermissions("sys:user:allot")
    @RequestMapping(value = "/allot/{id}", method = RequestMethod.GET)
    public String allotRolesInput(@PathVariable String id, @ModelAttribute UserForm userForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("分配角色错误：user.id对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("分配角色错误：user.id不能转换成Long类型！user.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (userId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        User user = userService.get(userId);
        if (user == null) {
            logger.error("分配角色错误：未查询到user.id={}的用户！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        userForm.setUser(user);
        modelMap.addAttribute("roles", roleService.findAll());
        modelMap.addAttribute(FORWARD_URL_KEY, "/user/list");
        return "user/allot";
    }

    @RequiresPermissions("sys:user:allot")
    @RequestMapping(value = "/allot", method = RequestMethod.POST)
    public String allotRoles(@Valid UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (userForm == null || userForm.getUser() == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
            return "redirect:/error";
        }

        if (userForm.getUser().getId() == null) {
            result.addError(new ObjectError("user.id", "用户编码不能为空！"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/allot/" + userForm.getUser().getId();
        }

        User user = userForm.getUser();

        User updateUser = userService.get(user.getId());

        updateUser.setRoles(user.getRoles());

        userService.update(updateUser);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/user/list");
        return "redirect:/success";
    }
}
