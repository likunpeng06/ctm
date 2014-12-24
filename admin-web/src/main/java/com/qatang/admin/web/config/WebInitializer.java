package com.qatang.admin.web.config;

import com.qatang.admin.web.controller.exception.WebExceptionHandler;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author qatang
 * @since 2014-12-19 14:43
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{InitConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class, WebExceptionHandler.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        return new Filter[]{filter};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        servletContext.addFilter("characterEncodingFilter", filter).addMappingForUrlPatterns(null, false, "/*");

        servletContext.addFilter("openEntityManagerInViewFilter", new OpenEntityManagerInViewFilter()).addMappingForUrlPatterns(null, false, "/*");

//        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
//        delegatingFilterProxy.setTargetFilterLifecycle(true);
//        servletContext.addFilter("shiroFilter", delegatingFilterProxy).addMappingForUrlPatterns(null, false, "/*");

        super.onStartup(servletContext);
    }
}
