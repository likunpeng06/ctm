package com.qatang.admin.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author qatang
 * @since 2014-12-19 15:28
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.qatang.*.controller")
public class WebConfig {
}
