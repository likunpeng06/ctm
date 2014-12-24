package com.qatang.admin.web.config;

import com.qatang.admin.web.shiro.authentication.RetryLimitHashedCredentialsMatcher;
import com.qatang.admin.web.shiro.realm.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @author qatang
 * @since 2014-12-19 15:27
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private Environment env;

    @Bean
    public CacheManager cacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager());
        credentialsMatcher.setHashAlgorithmName(env.getProperty("password.algorithmName"));
        credentialsMatcher.setHashIterations(Integer.valueOf(env.getProperty("password.hashIterations")));
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public Realm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/signin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.jsp");

        StringBuilder sb = new StringBuilder();
        sb.append("/resources/** = anon").append("\n");
        sb.append("/signin = anon").append("\n");
        sb.append("/signup = anon").append("\n");
        sb.append("/user/password/forget = anon").append("\n");
        sb.append("/kaptcha = anon").append("\n");
        sb.append("/signout = logout").append("\n");
        sb.append("/dashboard = user").append("\n");
        sb.append("/** = user");
        shiroFilterFactoryBean.setFilterChainDefinitions(sb.toString());
        return shiroFilterFactoryBean;
    }

    /**
     * 必须注册为static，不然env==null
     * 相关链接：https://issues.apache.org/jira/browse/SHIRO-222
     * http://stackoverflow.com/questions/15539485/specifying-shiros-lifecyclebeanpostprocessor-in-programmatic-spring-configurat
     *
     * LifecycleBeanPostProcessor 用于在实现了 Initializable 接口的 Shiro bean 初始化时调用 Initializable接口回调,在实现了Destroyable接口的Shiro bean销毁时调用Destroyable接 口回调。如 UserRealm 就实现了 Initializable,而 DefaultSecurityManager 实现了 Destroyable。
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

//    @Bean
//    @DependsOn(value="lifecycleBeanPostProcessor")
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//        return new DefaultAdvisorAutoProxyCreator();
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
//        return authorizationAttributeSourceAdvisor;
//    }

    @PostConstruct
    public void postConstruct() {
        SecurityUtils.setSecurityManager(securityManager());
    }
}
