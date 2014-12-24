package com.qatang.admin.web.config;

import com.qatang.admin.web.controller.exception.WebExceptionHandler;
import com.qatang.core.enums.converter.EnableDisableStatusConverter;
import com.qatang.core.enums.converter.YesNoStatusConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author qatang
 * @since 2014-12-19 15:28
 */
@Configuration
@Import(WebShiroConfig.class)
@EnableWebMvc
@ComponentScan(basePackages = "com.qatang.admin.web.controller", useDefaultFilters = false, includeFilters = @ComponentScan.Filter(value = {Controller.class, ControllerAdvice.class}))
public class WebConfig extends WebMvcConfigurerAdapter {
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
        registry.addViewController("/success").setViewName("success");
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
    }

    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new WebExceptionHandler();
    }
}
