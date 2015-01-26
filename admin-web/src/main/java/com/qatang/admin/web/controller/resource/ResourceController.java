package com.qatang.admin.web.controller.resource;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.admin.enums.ResourceType;
import com.qatang.admin.query.resource.ResourceSearchable;
import com.qatang.admin.service.resource.ResourceService;
import com.qatang.admin.web.form.resource.ResourceForm;
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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qatang
 * @since 2014-12-20 13:41
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {
    @Autowired
    private ResourceService resourceService;

    @ModelAttribute("enableDisableStatusList")
    public List<EnableDisableStatus> getEnableDisableStatusList() {
        return EnableDisableStatus.list();
    }

    @ModelAttribute("yesNoStatusList")
    public List<YesNoStatus> getYesNoStatusList() {
        return YesNoStatus.list();
    }

    @ModelAttribute("resourceTypeList")
    public List<ResourceType> getResourceTypeList() {
        return ResourceType.list();
    }

    @RequiresPermissions("sys:resource:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ResourceForm resourceForm, ModelMap modelMap) {
        Long parentId = null;
        if (resourceForm.getResource() != null
                && resourceForm.getResource().getParent() != null) {
            parentId = resourceForm.getResource().getParent().getId();
        }

        List<Resource> resourceList = resourceService.findByParentId(parentId);

        Resource parentResource = null;
        if (parentId != null) {
            parentResource = resourceService.get(parentId);
        }

        modelMap.addAttribute("parentResource", parentResource);
        modelMap.addAttribute("resourceList", resourceList);
        return "resource/list";
    }

    @RequiresPermissions("sys:resource:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createInput(@ModelAttribute ResourceForm resourceForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".resourceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        modelMap.addAttribute(FORWARD_URL_KEY, "/resource/list");
        return "resource/create";
    }

    @RequiresPermissions("sys:resource:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid ResourceForm resourceForm, BindingResult result, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (resourceForm == null || resourceForm.getResource() == null) {
            logger.error("新建资源错误：resourceForm或者resourceForm.resource对象为空");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        if (StringUtils.isEmpty(resourceForm.getResource().getName())) {
            result.addError(new ObjectError("resource.name", "资源名称不能为空！"));
        }

        if (resourceForm.getResource().getValid() == null) {
            result.addError(new ObjectError("resource.valid", "是否有效不能为空！"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(resourceForm);
            return "redirect:/resource/create";
        }

        Resource resource = resourceForm.getResource();
        if (resource.getParent() == null || resource.getParent().getId() == null) {
            resource.setParent(null);
            resource.setTreeLevel(0);
        } else {
            Resource parentResource = resourceService.get(resource.getParent().getId());

            int treeLevel = parentResource.getTreeLevel() + 1;
            resource.setTreeLevel(treeLevel);
            if (treeLevel == GlobalConstants.DEFAULT_TREE_LEVEL - 1) {
                resource.setEnd(true);
            }
        }
        resourceService.save(resource);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
        return "redirect:/success";
    }

    @RequiresPermissions("sys:resource:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateInput(@PathVariable String id, @ModelAttribute ResourceForm resourceForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("修改资源错误：resource.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }
        Long resourceId = null;
        try {
            resourceId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("修改资源错误：resource.id不能转换成Long类型！resource.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (resourceId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        Resource resource = resourceService.get(resourceId);
        if (resource == null) {
            logger.error("修改资源错误：未查询到resource.id={}的资源！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".resourceForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        resourceForm.setResource(resource);
        modelMap.addAttribute(FORWARD_URL_KEY, "/resource/list");
        return "resource/update";
    }

    @RequiresPermissions("sys:resource:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid ResourceForm resourceForm, BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (resourceForm == null || resourceForm.getResource() == null) {
            logger.error("修改资源错误：resourceForm或者resourceForm.resource对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        if (resourceForm.getResource().getId() == null) {
            result.addError(new ObjectError("resource.id", "资源编码不能为空！"));
        }

        if (resourceForm.getResource().getValid() == null) {
            result.addError(new ObjectError("resource.valid", "是否有效不能为空！"));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(resourceForm);
            return "redirect:/resource/update/" + resourceForm.getResource().getId();
        }

        Resource resource = resourceForm.getResource();

        Resource updateResource = resourceService.get(resource.getId());

        updateResource.setIdentifier(resource.getIdentifier());
        updateResource.setName(resource.getName());
        updateResource.setUrl(resource.getUrl());
        updateResource.setPriority(resource.getPriority());
        updateResource.setType(resource.getType());
        updateResource.setValid(resource.getValid());
        updateResource.setMemo(resource.getMemo());
        updateResource.setUpdatedTime(new Date());
        resourceService.update(updateResource);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
        return "redirect:/success";
    }

    @RequiresPermissions("sys:resource:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("查看资源错误：resource.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }
        Long resourceId = null;
        try {
            resourceId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("查看资源错误：resource.id不能转换成Long类型！resource.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (resourceId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        Resource resource = resourceService.get(resourceId);
        if (resource == null) {
            logger.error("查看资源错误：未查询到resource.id={}的资源！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        ResourceForm resourceForm = new ResourceForm();
        resourceForm.setResource(resource);
        modelMap.addAttribute(resourceForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/resource/list");
        return "resource/detail";
    }

    @RequiresPermissions("sys:resource:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(id)) {
            logger.error("删除资源错误：resource.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }
        Long resourceId = null;
        try {
            resourceId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("删除资源错误：resource.id不能转换成Long类型！resource.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (resourceId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        Resource resource = resourceService.get(resourceId);
        if (resource == null) {
            logger.error("删除资源错误：resource.id不能转换成Long类型！resource.id={}", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        resourceService.delete(resource.getId());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        return "redirect:/resource/list";
    }
}
