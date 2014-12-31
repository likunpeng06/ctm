package com.qatang.admin.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author qatang
 * @since 2014-12-19 15:25
 */
@Configuration
@PropertySource("classpath:config.properties")
@Import(value = {BeanConfig.class, ShiroConfig.class, JpaConfig.class})
public class InitConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
