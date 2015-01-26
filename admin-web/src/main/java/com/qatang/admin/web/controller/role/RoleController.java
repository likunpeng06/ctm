package com.qatang.admin.web.controller.role;

import com.alibaba.fastjson.JSON;
import com.qatang.admin.entity.resource.Resource;
import com.qatang.admin.entity.role.Role;
import com.qatang.admin.query.role.RoleSearchable;
import com.qatang.admin.service.resource.ResourceService;
import com.qatang.admin.service.role.RoleService;
import com.qatang.admin.web.form.role.RoleForm;
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
@RequestMapping("/role")
@SessionAttributes("roleSearchable")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

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

    @RequiresPermissions("sys:role:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(RoleSearchable roleSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, ModelMap modelMap) {
        roleSearchable = new RoleSearchable();
        roleSearchable.setPageable(pageable);

        Page<Role> page = roleService.find(roleSearchable);

        modelMap.addAttribute("page", page);
        return "role/list";
    }

    @RequiresPermissions("sys:role:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String search(RoleSearchable roleSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, ModelMap modelMap, HttpServletRequest request) {
        roleSearchable.setPageable(pageable);

        Page<Role> page = roleService.find(roleSearchable);

        modelMap.addAttribute("page", page);
        return "role/list";
    }

    @RequiresPermissions("sys:role:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createInput(@ModelAttribute RoleForm roleForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".roleForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        modelMap.addAttribute(FORWARD_URL_KEY, "/role/list");
        return "role/create";
    }

    @RequiresPermissions("sys:role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid RoleForm roleForm, BindingResult result, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (roleForm == null || roleForm.getRole() == null) {
            logger.error("新建角色错误：roleForm或者roleForm.role对象为空");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        if (StringUtils.isEmpty(roleForm.getRole().getIdentifier())) {
            result.addError(new ObjectError("role.identifier", "标识符不能为空！"));
        }

        if (StringUtils.isEmpty(roleForm.getRole().getName())) {
            result.addError(new ObjectError("role.name", "角色名称不能为空！"));
        }

        if (roleForm.getRole().getIsDefault() == null) {
            result.addError(new ObjectError("role.isDefault", "是否为默认角色不能为空！"));
        }

        if (roleForm.getRole().getValid() == null) {
            result.addError(new ObjectError("role.valid", "是否有效不能为空！"));
        }

        Role conRole = roleService.findByIdentifier(roleForm.getRole().getIdentifier());
        if (conRole != null) {
            result.addError(new ObjectError("role.identifier", "标识符已被使用！"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(roleForm);
            return "redirect:/role/create";
        }

        Role role = roleForm.getRole();
        roleService.save(role);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
        return "redirect:/success";
    }

    @RequiresPermissions("sys:role:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateInput(@PathVariable String id, @ModelAttribute RoleForm roleForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("修改角色错误：role.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        Long roleId = null;
        try {
            roleId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("修改角色错误：role.id不能转换成Long类型！role.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (roleId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        Role role = roleService.get(roleId);
        if (role == null) {
            logger.error("修改角色错误：未查询到role.id={}的角色！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
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

    @RequiresPermissions("sys:role:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid RoleForm roleForm, BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (roleForm == null || roleForm.getRole() == null) {
            logger.error("修改角色错误：roleForm或者roleForm.role对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        if (roleForm.getRole().getId() == null) {
            result.addError(new ObjectError("role.id", "角色编码不能为空！"));
        }

        if (roleForm.getRole().getIsDefault() == null) {
            result.addError(new ObjectError("role.isDefault", "是否为默认角色不能为空！"));
        }

        if (roleForm.getRole().getValid() == null) {
            result.addError(new ObjectError("role.valid", "是否有效不能为空！"));
        }

        Role conRole = roleService.findByIdentifier(roleForm.getRole().getIdentifier());
        if (conRole != null && conRole.getId().longValue() != roleForm.getRole().getId()) {
            result.addError(new ObjectError("role.identifier", "标识符已被使用！"));
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

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
        return "redirect:/success";
    }

    @RequiresPermissions("sys:role:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("查看角色错误：role.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        Long roleId = null;
        try {
            roleId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("查看角色错误：role.id不能转换成Long类型！role.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (roleId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        Role role = roleService.get(roleId);
        if (role == null) {
            logger.error("查看角色错误：未查询到role.id={}的角色！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        RoleForm roleForm = new RoleForm();
        roleForm.setRole(role);
        modelMap.addAttribute(roleForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/role/list");
        return "role/detail";
    }

    @RequiresPermissions("sys:role:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(id)) {
            logger.error("删除角色错误：role.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        Long roleId = null;
        try {
            roleId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("删除角色错误：role.id不能转换成Long类型！role.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (roleId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        Role role = roleService.get(roleId);
        if (role == null) {
            logger.error("删除角色错误：未查询到role.id={}的角色！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        roleService.delete(role.getId());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        return "redirect:/role/list";
    }

    @RequiresPermissions("sys:role:allot")
    @RequestMapping(value = "/allot/{id}", method = RequestMethod.GET)
    public String allotResourceInput(@PathVariable String id, @ModelAttribute RoleForm roleForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("分配资源错误：role.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }
        Long roleId = null;
        try {
            roleId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("分配资源错误：role.id不能转换成Long类型！role.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (roleId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        Role role = roleService.get(roleId);
        if (role == null) {
            logger.error("分配资源错误：未查询到role.id={}的角色！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".roleForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        roleForm.setRole(role);

        List<Resource> resources = resourceService.findAll();

        List<Resource> checkedResources = role.getResources();

        List<Map<String, Object>> resourcesJsonList = new ArrayList<>();
        for (Resource resource : resources) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", resource.getId());
            map.put("pId", resource.getParent() == null ? 0 : resource.getParent().getId());
            map.put("name", resource.getName());
            if (checkedResources.contains(resource)) {
                map.put("checked", true);
            }
            resourcesJsonList.add(map);
        }

        modelMap.addAttribute("resources", JSON.toJSONString(resourcesJsonList));
        modelMap.addAttribute(FORWARD_URL_KEY, "/role/list");
        return "role/allot";
    }

    @RequiresPermissions("sys:role:allot")
    @RequestMapping(value = "/allot", method = RequestMethod.POST)
    public String allotResource(@Valid RoleForm roleForm, BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (roleForm == null || roleForm.getRole() == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
            return "redirect:/error";
        }

        if (roleForm.getRole().getId() == null) {
            result.addError(new ObjectError("role.id", "角色编码不能为空！"));
        }

        Role conRole = roleService.findByIdentifier(roleForm.getRole().getIdentifier());
        if (conRole != null && conRole.getId().longValue() != roleForm.getRole().getId()) {
            result.addError(new ObjectError("role.identifier", "标识符已被使用！"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(roleForm);
            return "redirect:/role/allot/" + roleForm.getRole().getId();
        }

        Role role = roleForm.getRole();

        Role updateRole = roleService.get(role.getId());

        updateRole.setResources(role.getResources());

        roleService.update(updateRole);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/role/list");
        return "redirect:/success";
    }


//    @RequestMapping(value = "{roleId}/resource", produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String asyncGetData(@PathVariable Long roleId, Long parentId) {
//        List<Resource> resources = resourceService.findByParentId(parentId);
//        Role role = roleService.get(roleId);
//        if (role == null) {
//            logger.error("cannot find Role by roleId={}", roleId);
//            return null;
//        }
//
//        List<Resource> checkedResources = role.getResources();
//
//        List<Map<String, Object>> resourcesJsonList = new ArrayList<>();
//        for (Resource resource : resources) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", resource.getId());
//            map.put("pId", resource.getParent() == null ? 0 : resource.getParent().getId());
//            map.put("name", resource.getName());
//            if (checkedResources.contains(resource)) {
//                map.put("checked", true);
//            }
//            if (resource.getChildren() != null && resource.getChildren().size() > 0) {
//                map.put("isParent", true);
//            }
//            resourcesJsonList.add(map);
//        }
//        return JSON.toJSONString(resourcesJsonList);
//    }
}
