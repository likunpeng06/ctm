package com.qatang.admin.web.interceptor;

import com.qatang.admin.entity.log.Log;
import com.qatang.admin.entity.resource.Resource;
import com.qatang.admin.entity.role.Role;
import com.qatang.admin.entity.user.User;
import com.qatang.admin.enums.ResourceType;
import com.qatang.admin.service.log.LogService;
import com.qatang.admin.service.user.UserService;
import com.qatang.core.enums.EnableDisableStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author qatang
 * @since 2015-01-22 13:32
 */
@Component
public class DefaultInterceptor extends HandlerInterceptorAdapter implements ServletContextAware {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PatternMatcher matcher = new AntPathMatcher();

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Log log = new Log();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return true;
        }
        user = userService.get(user.getId());
        if (user == null) {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            response.sendRedirect("/sigin");
            logger.error("未发现当前用户，无法记录操作日志");
            return false;
        }

        try {
            log.setUser(user);
            log.setUrl(request.getRequestURI().substring(request.getContextPath().length()));
            log.setParams(convertParamsToString(request.getParameterMap()));
            logService.save(log);
        } catch (Exception e) {
            logger.error("保存日志失败，请求继续处理。");
            logger.error(e.getMessage(), e);
        }

        return true;
    }

    private String convertParamsToString(Map<String, String[]> parameters) {
        StringBuilder sb = new StringBuilder();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);

            StringBuilder subSb = new StringBuilder();
            for (String value1 : values) {
                subSb.append(value1).append("|");
            }
            if (subSb.length() > 0) {
                subSb.deleteCharAt(subSb.length() - 1);
            }
            sb.append(key).append("=").append(subSb.toString()).append("&");

        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            return;
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return;
        }
        user = userService.get(user.getId());
        if (user == null) {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            response.sendRedirect("/sigin");
            return;
        }

        String uri = request.getRequestURI().substring(request.getContextPath().length());

        Resource currentResource = null;
        Set<Resource> resourceSet = new HashSet<>();
        List<Resource> breadcrumbList = new ArrayList<>();
        //过滤
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            List<Role> roles = user.getRoles();
            for (Role role : roles) {
                if (role.getValid() == EnableDisableStatus.ENABLE && role.getResources() != null && role.getResources().size() > 0) {
                    for (Resource resource : role.getResources()) {
                        if (resource.getValid() == EnableDisableStatus.ENABLE) {
                            if (breadcrumbList.size() == 0) {
                                String path = resource.getUrl();
                                if (path != null && matcher.matches(path, uri)) {
                                    currentResource = resource;

                                    Resource breadcrumbResource = resource;
                                    while (breadcrumbResource != null) {
                                        breadcrumbList.add(0, breadcrumbResource);
                                        breadcrumbResource = breadcrumbResource.getParent();
                                    }
                                }
                            }

                            if (resource.getType() == ResourceType.MENU) {
                                resourceSet.add(resource);
                            }
                        }
                    }
                }
            }
        }
        //组合
        List<Resource> menuList = new ArrayList<>();
        for (Resource resource : resourceSet) {
            Resource parent = resource.getParent();

            if (parent == null) {
                if (!menuList.contains(resource)) {
                    resource.setChildren(new ArrayList<>());
                    menuList.add(resource);
                }
                continue;
            }

            if (!menuList.contains(parent)) {
                parent.setChildren(new ArrayList<>());
                menuList.add(parent);
            }

            List<Resource> children = parent.getChildren();
            children.add(resource);
        }
        //排序
        for (Resource menu : menuList) {
            sort(menu.getChildren());
        }
        sort(menuList);

        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("currentResource", currentResource);
        modelAndView.addObject("menus", menuList.size() == 0 ? null : menuList);
        modelAndView.addObject("breadcrumb", breadcrumbList.size() == 0 ? null : breadcrumbList);
    }

    private void sort(List<Resource> menuList) {
        Collections.sort(menuList, new Comparator<Resource>() {
            @Override
            public int compare(Resource o1, Resource o2) {
                if (o1.getPriority() != o2.getPriority()) {
                    return o2.getPriority() - o1.getPriority();
                }
                return (int)(o1.getId() - o2.getId());
            }
        });
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        logger.error("admin-web started!");
    }
}
