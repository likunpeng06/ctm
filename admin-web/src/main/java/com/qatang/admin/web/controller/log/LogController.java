package com.qatang.admin.web.controller.log;

import com.qatang.admin.entity.log.Log;
import com.qatang.admin.query.log.LogSearchable;
import com.qatang.admin.service.log.LogService;
import com.qatang.admin.service.user.UserService;
import com.qatang.admin.web.form.log.LogForm;
import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qatang
 * @since 2014-12-20 13:41
 */
@Controller
@RequestMapping("/log")
@SessionAttributes("logSearchable")
public class LogController extends BaseController {
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @RequiresPermissions("sys:log:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(LogSearchable logSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        logSearchable = new LogSearchable();
        logSearchable.setPageable(pageable);

        Page<Log> page = logService.find(logSearchable);

        modelMap.addAttribute("page", page);
        return "log/list";
    }

    @RequiresPermissions("sys:log:list")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String search(LogSearchable logSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap, HttpServletRequest request) {
        logSearchable.setPageable(pageable);

//        if (StringUtils.isNotEmpty(logSearchable.getUsername())) {
//            User user = userService.findByUsername(logSearchable.getUsername());
//            if (user != null) {
//                logSearchable.setUserId(user.getId());
//            }
//        }

        Page<Log> page = logService.find(logSearchable);

        modelMap.addAttribute("page", page);
        return "log/list";
    }

    @RequiresPermissions("sys:log:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (StringUtils.isEmpty(id)) {
            logger.error("查看日志错误：log.id不能为空！");
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/log/list");
            return "redirect:/error";
        }
        Long logId = null;
        try {
            logId = Long.valueOf(id);
        } catch (Exception e) {
            logger.error("查看日志错误：log.id不能转换成Long类型！log.id={}", id);
            logger.error(e.getMessage(), e);
        }
        if (logId == null) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/log/list");
            return "redirect:/error";
        }

        Log log = logService.get(logId);
        if (log == null) {
            logger.error("查看日志错误：未查询到log.id={}的日志！", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据！");
            redirectAttributes.addFlashAttribute(FORWARD_URL_KEY, "/log/list");
            return "redirect:/error";
        }

        LogForm logForm = new LogForm();
        logForm.setLog(log);
        modelMap.addAttribute(logForm);
        modelMap.addAttribute(FORWARD_URL_KEY, "/log/list");
        return "log/detail";
    }
}
