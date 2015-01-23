package com.qatang.admin.web.interceptor;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.admin.entity.role.Role;
import com.qatang.admin.entity.user.User;
import com.qatang.admin.enums.ResourceType;
import com.qatang.admin.service.user.UserService;
import com.qatang.core.enums.EnableDisableStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author qatang
 * @since 2015-01-22 13:32
 */
@Component
public class MenuInterceptor extends HandlerInterceptorAdapter {
    private PatternMatcher matcher = new AntPathMatcher();

    @Autowired
    private UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            return;
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
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
}
