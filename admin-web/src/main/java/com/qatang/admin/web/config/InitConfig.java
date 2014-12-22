package com.qatang.admin.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author qatang
 * @since 2014-12-19 15:25
 */
@Configuration
//@Import(value = {BeanConfig.class, JpaConfig.class, ShiroConfig.class})
@Import(value = {BeanConfig.class, JpaConfig.class})
public class InitConfig {

}
