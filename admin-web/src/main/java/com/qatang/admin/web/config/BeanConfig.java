package com.qatang.admin.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author qatang
 * @since 2014-12-19 15:27
 */
@Configuration
@ComponentScan(basePackages = "com.qatang", excludeFilters = @ComponentScan.Filter(value = {Controller.class, ControllerAdvice.class}))
public class BeanConfig {
}
