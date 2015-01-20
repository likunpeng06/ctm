package com.qatang.admin.web.controller.role;

import com.qatang.admin.entity.role.Role;
import com.qatang.admin.query.role.RoleSearchable;
import com.qatang.admin.service.role.RoleService;
import com.qatang.admin.web.form.role.RoleForm;
import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.controller.BaseController;
import com.qatang.core.enums.EnableDisableStatus;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qatang
 * @since 2014-12-20 13:41
 */
@Controller
@RequestMapping("/role")
@SessionAttributes("roleSearchable")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @ModelAttribute("orderFieldMap")
    public Map<String, String> getOrderFieldList() {
        Map<String, String> orderFieldMap = new LinkedHashMap<>();
        orderFieldMap.put("id,asc", "编码从小到大");
        orderFieldMap.put("id,desc", "编码从大到小");
        orderFieldMap.put("createdTime,asc", "创建时间从早到晚");
        orderFieldMap.put("createdTime,desc", "创建时间从晚到早");
        return orderFieldMap;
    }

    @ModelAttribute("enableDisableStatusList")
    public List<EnableDisableStatus> getEnableDisableStatusList() {
        return EnableDisableStatus.list();
    }

    @ModelAttribute("yesNoStatusList")
    public List<YesNoStatus> getYesNoStatusList() {
        return YesNoStatus.list();
    }

//    @RequiresPermissions("sys:role:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(RoleSearchable roleSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, ModelMap modelMap) {
        roleSearchable = new RoleSearchable();
        roleSearchable.setPageable(pageable);

        Page<Role> page = roleService.findAll(roleSearchable);

        modelMap.addAttribute("page", page);
        return "role/list";
    }

//    @RequiresPermissions("sys:role:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String search(RoleSearchable roleSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, ModelMap modelMap, HttpServletRequest request) {
        roleSearchable.setPageable(pageable);

        Page<Role> page = roleService.findAll(roleSearchable);

        modelMap.addAttribute("page", page);
        return "role/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createInput(@ModelAttribute RoleForm roleForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".roleForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        modelMap.addAttribute(FORWARD_URL_KEY, "/role/list");
        return "role/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid RoleForm roleForm, BindingResult result, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (roleForm == null || roleForm.getRole() == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.data}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        if (StringUtils.isEmpty(roleForm.getRole().getIdentifier())) {
            result.addError(new ObjectError("role.identifier", "{not.null}"));
        }

        if (StringUtils.isEmpty(roleForm.getRole().getName())) {
            result.addError(new ObjectError("role.name", "{not.null}"));
        }

        if (roleForm.getRole().getIsDefault() == null) {
            result.addError(new ObjectError("role.isDefault", "{role.isDefault.not.null}"));
        }

        if (roleForm.getRole().getValid() == null) {
            result.addError(new ObjectError("role.valid", "{role.valid.not.null}"));
        }

        Role conRole = roleService.findByIdentifier(roleForm.getRole().getIdentifier());
        if (conRole != null) {
            result.addError(new ObjectError("role.identifier", "{identifier.has.been.used}"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(roleForm);
            return "redirect:/role/create";
        }

        Role role = roleForm.getRole();
        roleService.save(role);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "{success}");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
        return "redirect:/success";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateInput(@PathVariable String id, @ModelAttribute RoleForm roleForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        Long roleId = null;
        try {
            roleId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (roleId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        Role role = roleService.get(roleId);
        if (role == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".roleForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        roleForm.setRole(role);
        modelMap.addAttribute(FORWARD_URL_KEY, "/role/list");
        return "role/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid RoleForm roleForm, BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (roleForm == null || roleForm.getRole() == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.data}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        if (roleForm.getRole().getId() == null) {
            result.addError(new ObjectError("role.id", "{role.id.not.null}"));
        }

        if (roleForm.getRole().getIsDefault() == null) {
            result.addError(new ObjectError("role.isDefault", "{role.isDefault.not.null}"));
        }

        if (roleForm.getRole().getValid() == null) {
            result.addError(new ObjectError("role.valid", "{role.valid.not.null}"));
        }

        Role conRole = roleService.findByIdentifier(roleForm.getRole().getIdentifier());
        if (conRole != null && conRole.getId().longValue() != roleForm.getRole().getId()) {
            result.addError(new ObjectError("role.identifier", "{identifier.has.been.used}"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(roleForm);
            return "redirect:/role/update/" + roleForm.getRole().getId();
        }

        Role role = roleForm.getRole();

        Role updateRole = roleService.get(role.getId());
        updateRole.setIdentifier(role.getIdentifier());
        updateRole.setName(role.getName());
        updateRole.setDescription(role.getDescription());
        updateRole.setIsDefault(role.getIsDefault());
        updateRole.setValid(role.getValid());
        updateRole.setUpdatedTime(new Date());
        roleService.update(updateRole);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "{success}");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
        return "redirect:/success";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        Long roleId = null;
        try {
            roleId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (roleId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        Role role = roleService.get(roleId);
        if (role == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        RoleForm roleForm = new RoleForm();
        roleForm.setRole(role);
        modelMap.addAttribute(roleForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/role/list");
        return "role/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        Long roleId = null;
        try {
            roleId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (roleId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        Role role = roleService.get(roleId);
        if (role == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        roleService.delete(role.getId());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "{delete.success}");
        return "redirect:/role/list";
    }
}
