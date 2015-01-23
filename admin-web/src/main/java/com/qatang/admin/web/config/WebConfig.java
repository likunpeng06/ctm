package com.qatang.admin.web.config;

import com.qatang.admin.enums.converter.ResourceTypeConverter;
import com.qatang.admin.web.controller.exception.WebExceptionHandler;
import com.qatang.admin.web.interceptor.MenuInterceptor;
import com.qatang.core.enums.converter.EnableDisableStatusConverter;
import com.qatang.core.enums.converter.YesNoStatusConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * @author qatang
 * @since 2014-12-19 15:28
 */
@Configuration
@Import(WebShiroConfig.class)
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "com.qatang.admin.web.controller", useDefaultFilters = false, includeFilters = @ComponentScan.Filter(value = {Controller.class, ControllerAdvice.class}))
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private MenuInterceptor menuInterceptor;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/error").setViewName("failure");
        registry.addViewController("/").setViewName("redirect:/dashboard");
        registry.addViewController("/success").setViewName("success");
        registry.addViewController("/unauthorized").setViewName("unauthorized");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addConverter(new YesNoStatusConverter());
        registry.addConverter(new EnableDisableStatusConverter());
        registry.addConverter(new ResourceTypeConverter());

        DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss");
        dateFormatter.setLenient(true);
        registry.addFormatter(dateFormatter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);

//        argumentResolvers.add(new SearchFilterMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(menuInterceptor);
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/resources/**");
        interceptorRegistration.excludePathPatterns("/signin");
        interceptorRegistration.excludePathPatterns("/signup");
        interceptorRegistration.excludePathPatterns("/kaptcha");
        interceptorRegistration.excludePathPatterns("/user/password/forget");
        interceptorRegistration.excludePathPatterns("/signout");
    }

    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new WebExceptionHandler();
    }
}
