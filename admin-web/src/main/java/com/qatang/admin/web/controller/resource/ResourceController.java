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
@SessionAttributes("resourceSearchable")
public class ResourceController extends BaseController {
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

    @ModelAttribute("resourceTypeList")
    public List<ResourceType> getResourceTypeList() {
        return ResourceType.list();
    }

//    @RequiresPermissions("sys:resource:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ResourceSearchable resourceSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "orderField", direction = Sort.Direction.ASC) Pageable pageable, ModelMap modelMap) {
        resourceSearchable = new ResourceSearchable();
        resourceSearchable.setPageable(pageable);

        Page<Resource> page = resourceService.findAll(resourceSearchable);

        modelMap.addAttribute("page", page);
        return "resource/list";
    }

//    @RequiresPermissions("sys:resource:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String search(ResourceSearchable resourceSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "orderField", direction = Sort.Direction.ASC) Pageable pageable, ModelMap modelMap, HttpServletRequest request) {
        resourceSearchable.setPageable(pageable);

        Page<Resource> page = resourceService.findAll(resourceSearchable);

        modelMap.addAttribute("page", page);
        return "resource/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createInput(@ModelAttribute ResourceForm resourceForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".resourceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        modelMap.addAttribute(FORWARD_URL_KEY, "/resource/list");
        return "resource/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid ResourceForm resourceForm, BindingResult result, ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (resourceForm == null || resourceForm.getResource() == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.data}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        if (StringUtils.isEmpty(resourceForm.getResource().getName())) {
            result.addError(new ObjectError("resource.name", "{not.null}"));
        }

        if (resourceForm.getResource().getValid() == null) {
            result.addError(new ObjectError("resource.valid", "{resource.valid.not.null}"));
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
        Resource resourceNew = resourceService.save(resource);

        String orderField = "";
        if (resourceNew.getParent() != null) {
            Resource resourceParent = resourceService.get(resourceNew.getParent().getId());
            orderField = resourceParent.getOrderField();
        }
        StringBuilder sb = new StringBuilder(orderField);
        if (resourceNew.getParent() != null) {
            sb.append("_");
        }
        sb.append(resourceNew.getId());

        resourceNew.setOrderField(sb.toString());
        resourceService.update(resourceNew);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "{success}");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
        return "redirect:/success";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateInput(@PathVariable String id, @ModelAttribute ResourceForm resourceForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }
        Long resourceId = null;
        try {
            resourceId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (resourceId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        Resource resource = resourceService.get(resourceId);
        if (resource == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid ResourceForm resourceForm, BindingResult result, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (resourceForm == null || resourceForm.getResource() == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.data}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        if (resourceForm.getResource().getId() == null) {
            result.addError(new ObjectError("resource.id", "{resource.id.not.null}"));
        }

        if (resourceForm.getResource().getValid() == null) {
            result.addError(new ObjectError("resource.valid", "{resource.valid.not.null}"));
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

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "{success}");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
        return "redirect:/success";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }
        Long resourceId = null;
        try {
            resourceId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (resourceId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        Resource resource = resourceService.get(resourceId);
        if (resource == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        ResourceForm resourceForm = new ResourceForm();
        resourceForm.setResource(resource);
        modelMap.addAttribute(resourceForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/resource/list");
        return "resource/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(id)) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }
        Long resourceId = null;
        try {
            resourceId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (resourceId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        Resource resource = resourceService.get(resourceId);
        if (resource == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "{illegal.id}");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/resource/list");
            return "redirect:/error";
        }

        resourceService.delete(resource.getId());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "{delete.success}");
        return "redirect:/resource/list";
    }
}
