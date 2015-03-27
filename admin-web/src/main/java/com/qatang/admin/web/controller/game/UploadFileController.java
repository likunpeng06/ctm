package com.qatang.admin.web.controller.game;

import com.qatang.admin.entity.game.UploadFile;
import com.qatang.admin.query.game.UploadFileSearchable;
import com.qatang.admin.service.game.UploadFileService;
import com.qatang.admin.web.form.game.UploadFileForm;
import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.controller.BaseController;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author qatang
 * @since 2014-12-20 13:41
 */
@Controller
@RequestMapping("/upload")
public class UploadFileController extends BaseController {
    @Autowired
    private UploadFileService uploadFileService;

    @RequiresPermissions("operation:upload:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(UploadFileSearchable uploadFileSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        uploadFileSearchable = new UploadFileSearchable();
        uploadFileSearchable.setPageable(pageable);

        Page<UploadFile> page = uploadFileService.find(uploadFileSearchable);

        modelMap.addAttribute("page", page);
        return "upload/list";
    }

    @RequiresPermissions("operation:upload:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String search(UploadFileSearchable uploadFileSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap, HttpServletRequest request) {
        uploadFileSearchable.setPageable(pageable);

        Page<UploadFile> page = uploadFileService.find(uploadFileSearchable);

        modelMap.addAttribute("page", page);
        return "upload/list";
    }

    @RequiresPermissions("operation:upload:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createInput(@ModelAttribute UploadFileForm uploadFileForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".uploadFileForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        modelMap.addAttribute(FORWARD_URL_KEY, "/upload/list");
        return "upload/create";
    }

    @RequiresPermissions("operation:upload:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(UploadFileForm uploadFileForm, MultipartFile file, BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result);
            redirectAttributes.addFlashAttribute(uploadFileForm);
            return "redirect:/upload/create";
        }

        String path = null;
        if (!file.isEmpty()) {
            ServletContext sc = request.getSession().getServletContext();
            path = File.separator + "resources" + File.separator + "upload" + File.separator + System.currentTimeMillis() + ".jpg";
            File target = new File(sc.getRealPath("/") + path);

            try {
                FileUtils.writeByteArrayToFile(target, file.getBytes());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        UploadFile uploadFile = uploadFileForm.getUploadFile();
        uploadFile.setUrl(path);
        uploadFileService.save(uploadFile);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/upload/list");
        return "redirect:/success";
    }

    @RequiresPermissions("operation:upload:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("查看上传文件错误：uploadFile.id对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/upload/list");
            return "redirect:/error";
        }
        Long uploadFileId = null;
        try {
            uploadFileId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("查看上传文件错误：uploadFile.id不能转换成Long类型！uploadFile.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (uploadFileId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/upload/list");
            return "redirect:/error";
        }

        UploadFile uploadFile = uploadFileService.get(uploadFileId);
        if (uploadFile == null) {
            logger.error("查询上传文件错误：未查询到uploadFile.id={}的上传文件！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/upload/list");
            return "redirect:/error";
        }

        UploadFileForm uploadFileForm = new UploadFileForm();
        uploadFileForm.setUploadFile(uploadFile);
        modelMap.addAttribute(uploadFileForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/upload/list");
        return "upload/detail";
    }

    @RequiresPermissions("operation:upload:delete")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(id)) {
            logger.error("删除上传文件错误：uploadFile.id对象不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/upload/list");
            return "redirect:/error";
        }
        Long uploadFileId = null;
        try {
            uploadFileId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("删除上传文件错误：uploadFile.id不能转换成Long类型！uploadFile.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (uploadFileId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/upload/list");
            return "redirect:/error";
        }

        UploadFile uploadFile = uploadFileService.get(uploadFileId);
        if (uploadFile == null) {
            logger.error("删除上传文件错误：未查询到uploadFile.id={}的上传文件！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/upload/list");
            return "redirect:/error";
        }

        uploadFileService.delete(uploadFile.getId());
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "操作成功！");
        return "redirect:/upload/list";
    }

}
