package com.qatang.admin.web.config;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.admin.entity.role.Role;
import com.qatang.admin.entity.user.User;
import com.qatang.admin.enums.ResourceType;
import com.qatang.admin.service.resource.ResourceService;
import com.qatang.admin.service.role.RoleService;
import com.qatang.admin.service.user.UserService;
import com.qatang.admin.web.shiro.authentication.PasswordHelper;
import com.qatang.core.enums.EnableDisableStatus;
import com.qatang.core.enums.YesNoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qatang
 * @since 2014-12-19 15:25
 */
@Configuration
@PropertySource("classpath:config.properties")
@Import(value = {BeanConfig.class, ShiroConfig.class, JpaConfig.class})
public class InitConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PasswordHelper passwordHelper;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @PostConstruct
    public void initData() {
        long count = userService.count(null);
        if (count == 0) {

            List<Resource> role1ResourceList = new ArrayList<>();
            List<Resource> role2ResourceList = new ArrayList<>();

            {
                Resource resource1 = new Resource();
//              resource1.setIdentifier("");
                resource1.setName("系统管理");
//              resource1.setUrl("");
                resource1.setValid(EnableDisableStatus.ENABLE);
                resource1.setTreeLevel(0);
                resource1.setEnd(false);
                resource1.setParent(null);
                resource1.setPriority(0);
                resource1.setType(ResourceType.MENU);
                resource1 = resourceService.save(resource1);
                role1ResourceList.add(resource1);

                Resource resource1_1 = new Resource();
//              resource1_1.setIdentifier("");
                resource1_1.setName("用户管理");
                resource1_1.setUrl("/user/list");
                resource1_1.setValid(EnableDisableStatus.ENABLE);
                resource1_1.setTreeLevel(1);
                resource1_1.setEnd(false);
                resource1_1.setParent(resource1);
                resource1_1.setPriority(0);
                resource1_1.setType(ResourceType.MENU);
                resource1_1 = resourceService.save(resource1_1);
                role1ResourceList.add(resource1_1);
                {
                    Resource resource1_1_1 = new Resource();
                    resource1_1_1.setIdentifier("sys:user:list");
                    resource1_1_1.setName("用户列表");
                    resource1_1_1.setUrl("/user/list");
                    resource1_1_1.setValid(EnableDisableStatus.ENABLE);
                    resource1_1_1.setTreeLevel(2);
                    resource1_1_1.setEnd(true);
                    resource1_1_1.setParent(resource1_1);
                    resource1_1_1.setPriority(0);
                    resource1_1_1.setType(ResourceType.FUNCTION);
                    resource1_1_1 = resourceService.save(resource1_1_1);
                    role1ResourceList.add(resource1_1_1);

                    Resource resource1_1_2 = new Resource();
                    resource1_1_2.setIdentifier("sys:user:create");
                    resource1_1_2.setName("用户添加");
                    resource1_1_2.setUrl("/user/create");
                    resource1_1_2.setValid(EnableDisableStatus.ENABLE);
                    resource1_1_2.setTreeLevel(2);
                    resource1_1_2.setEnd(true);
                    resource1_1_2.setParent(resource1_1);
                    resource1_1_2.setPriority(0);
                    resource1_1_2.setType(ResourceType.FUNCTION);
                    resource1_1_2 = resourceService.save(resource1_1_2);
                    role1ResourceList.add(resource1_1_2);

                    Resource resource1_1_3 = new Resource();
                    resource1_1_3.setIdentifier("sys:user:update");
                    resource1_1_3.setName("用户修改");
                    resource1_1_3.setUrl("/user/update/**");
                    resource1_1_3.setValid(EnableDisableStatus.ENABLE);
                    resource1_1_3.setTreeLevel(2);
                    resource1_1_3.setEnd(true);
                    resource1_1_3.setParent(resource1_1);
                    resource1_1_3.setPriority(0);
                    resource1_1_3.setType(ResourceType.FUNCTION);
                    resource1_1_3 = resourceService.save(resource1_1_3);
                    role1ResourceList.add(resource1_1_3);

                    Resource resource1_1_4 = new Resource();
                    resource1_1_4.setIdentifier("sys:user:view");
                    resource1_1_4.setName("用户查看");
                    resource1_1_4.setUrl("/user/view/**");
                    resource1_1_4.setValid(EnableDisableStatus.ENABLE);
                    resource1_1_4.setTreeLevel(2);
                    resource1_1_4.setEnd(true);
                    resource1_1_4.setParent(resource1_1);
                    resource1_1_4.setPriority(0);
                    resource1_1_4.setType(ResourceType.FUNCTION);
                    resource1_1_4 = resourceService.save(resource1_1_4);
                    role1ResourceList.add(resource1_1_4);

                    Resource resource1_1_5 = new Resource();
                    resource1_1_5.setIdentifier("sys:user:delete");
                    resource1_1_5.setName("用户删除");
                    resource1_1_5.setUrl("/user/delete/**");
                    resource1_1_5.setValid(EnableDisableStatus.ENABLE);
                    resource1_1_5.setTreeLevel(2);
                    resource1_1_5.setEnd(true);
                    resource1_1_5.setParent(resource1_1);
                    resource1_1_5.setPriority(0);
                    resource1_1_5.setType(ResourceType.FUNCTION);
                    resource1_1_5 = resourceService.save(resource1_1_5);
                    role1ResourceList.add(resource1_1_5);

                    Resource resource1_1_6 = new Resource();
                    resource1_1_6.setIdentifier("sys:user:allot");
                    resource1_1_6.setName("分配角色");
                    resource1_1_6.setUrl("/user/allot/**");
                    resource1_1_6.setValid(EnableDisableStatus.ENABLE);
                    resource1_1_6.setTreeLevel(2);
                    resource1_1_6.setEnd(true);
                    resource1_1_6.setParent(resource1_1);
                    resource1_1_6.setPriority(0);
                    resource1_1_6.setType(ResourceType.FUNCTION);
                    resource1_1_6 = resourceService.save(resource1_1_6);
                    role1ResourceList.add(resource1_1_6);
                }

                Resource resource1_2 = new Resource();
//              resource1_2.setIdentifier("");
                resource1_2.setName("角色管理");
                resource1_2.setUrl("/role/list");
                resource1_2.setValid(EnableDisableStatus.ENABLE);
                resource1_2.setTreeLevel(1);
                resource1_2.setEnd(false);
                resource1_2.setParent(resource1);
                resource1_2.setPriority(0);
                resource1_2.setType(ResourceType.MENU);
                resource1_2 = resourceService.save(resource1_2);
                role1ResourceList.add(resource1_2);
                {
                    Resource resource1_2_1 = new Resource();
                    resource1_2_1.setIdentifier("sys:role:list");
                    resource1_2_1.setName("角色列表");
                    resource1_2_1.setUrl("/role/list");
                    resource1_2_1.setValid(EnableDisableStatus.ENABLE);
                    resource1_2_1.setTreeLevel(2);
                    resource1_2_1.setEnd(true);
                    resource1_2_1.setParent(resource1_2);
                    resource1_2_1.setPriority(0);
                    resource1_2_1.setType(ResourceType.FUNCTION);
                    resource1_2_1 = resourceService.save(resource1_2_1);
                    role1ResourceList.add(resource1_2_1);

                    Resource resource1_2_2 = new Resource();
                    resource1_2_2.setIdentifier("sys:role:create");
                    resource1_2_2.setName("角色添加");
                    resource1_2_2.setUrl("/role/create");
                    resource1_2_2.setValid(EnableDisableStatus.ENABLE);
                    resource1_2_2.setTreeLevel(2);
                    resource1_2_2.setEnd(true);
                    resource1_2_2.setParent(resource1_2);
                    resource1_2_2.setPriority(0);
                    resource1_2_2.setType(ResourceType.FUNCTION);
                    resource1_2_2 = resourceService.save(resource1_2_2);
                    role1ResourceList.add(resource1_2_2);

                    Resource resource1_2_3 = new Resource();
                    resource1_2_3.setIdentifier("sys:role:update");
                    resource1_2_3.setName("角色修改");
                    resource1_2_3.setUrl("/role/update/**");
                    resource1_2_3.setValid(EnableDisableStatus.ENABLE);
                    resource1_2_3.setTreeLevel(2);
                    resource1_2_3.setEnd(true);
                    resource1_2_3.setParent(resource1_2);
                    resource1_2_3.setPriority(0);
                    resource1_2_3.setType(ResourceType.FUNCTION);
                    resource1_2_3 = resourceService.save(resource1_2_3);
                    role1ResourceList.add(resource1_2_3);

                    Resource resource1_2_4 = new Resource();
                    resource1_2_4.setIdentifier("sys:role:view");
                    resource1_2_4.setName("角色查看");
                    resource1_2_4.setUrl("/role/view/**");
                    resource1_2_4.setValid(EnableDisableStatus.ENABLE);
                    resource1_2_4.setTreeLevel(2);
                    resource1_2_4.setEnd(true);
                    resource1_2_4.setParent(resource1_2);
                    resource1_2_4.setPriority(0);
                    resource1_2_4.setType(ResourceType.FUNCTION);
                    resource1_2_4 = resourceService.save(resource1_2_4);
                    role1ResourceList.add(resource1_2_4);

                    Resource resource1_2_5 = new Resource();
                    resource1_2_5.setIdentifier("sys:role:delete");
                    resource1_2_5.setName("角色删除");
                    resource1_2_5.setUrl("/role/delete/**");
                    resource1_2_5.setValid(EnableDisableStatus.ENABLE);
                    resource1_2_5.setTreeLevel(2);
                    resource1_2_5.setEnd(true);
                    resource1_2_5.setParent(resource1_2);
                    resource1_2_5.setPriority(0);
                    resource1_2_5.setType(ResourceType.FUNCTION);
                    resource1_2_5 = resourceService.save(resource1_2_5);
                    role1ResourceList.add(resource1_2_5);

                    Resource resource1_2_6 = new Resource();
                    resource1_2_6.setIdentifier("sys:role:allot");
                    resource1_2_6.setName("分配资源");
                    resource1_2_6.setUrl("/role/allot/**");
                    resource1_2_6.setValid(EnableDisableStatus.ENABLE);
                    resource1_2_6.setTreeLevel(2);
                    resource1_2_6.setEnd(true);
                    resource1_2_6.setParent(resource1_2);
                    resource1_2_6.setPriority(0);
                    resource1_2_6.setType(ResourceType.FUNCTION);
                    resource1_2_6 = resourceService.save(resource1_2_6);
                    role1ResourceList.add(resource1_2_6);

//                    Resource resource1_2_7 = new Resource();
//                    resource1_2_7.setIdentifier("sys:role:resource");
//                    resource1_2_7.setName("角色资源数据");
//                    resource1_2_7.setUrl("/role/**/resource");
//                    resource1_2_7.setValid(EnableDisableStatus.ENABLE);
//                    resource1_2_7.setTreeLevel(2);
//                    resource1_2_7.setEnd(true);
//                    resource1_2_7.setParent(resource1_2);
//                    resource1_2_7.setPriority(0);
//                    resource1_2_7.setType(ResourceType.FUNCTION);
//                    resource1_2_7 = resourceService.save(resource1_2_7);
//                    role1ResourceList.add(resource1_2_7);
                }

                Resource resource1_3 = new Resource();
//              resource1_3.setIdentifier("");
                resource1_3.setName("资源管理");
                resource1_3.setUrl("/resource/list");
                resource1_3.setValid(EnableDisableStatus.ENABLE);
                resource1_3.setTreeLevel(1);
                resource1_3.setEnd(false);
                resource1_3.setParent(resource1);
                resource1_3.setPriority(0);
                resource1_3.setType(ResourceType.MENU);
                resource1_3 = resourceService.save(resource1_3);
                role1ResourceList.add(resource1_3);
                {
                    Resource resource1_3_1 = new Resource();
                    resource1_3_1.setIdentifier("sys:resource:list");
                    resource1_3_1.setName("资源列表");
                    resource1_3_1.setUrl("/resource/list");
                    resource1_3_1.setValid(EnableDisableStatus.ENABLE);
                    resource1_3_1.setTreeLevel(2);
                    resource1_3_1.setEnd(true);
                    resource1_3_1.setParent(resource1_3);
                    resource1_3_1.setPriority(0);
                    resource1_3_1.setType(ResourceType.FUNCTION);
                    resource1_3_1 = resourceService.save(resource1_3_1);
                    role1ResourceList.add(resource1_3_1);

                    Resource resource1_3_2 = new Resource();
                    resource1_3_2.setIdentifier("sys:resource:create");
                    resource1_3_2.setName("资源添加");
                    resource1_3_2.setUrl("/resource/create");
                    resource1_3_2.setValid(EnableDisableStatus.ENABLE);
                    resource1_3_2.setTreeLevel(2);
                    resource1_3_2.setEnd(true);
                    resource1_3_2.setParent(resource1_3);
                    resource1_3_2.setPriority(0);
                    resource1_3_2.setType(ResourceType.FUNCTION);
                    resource1_3_2 = resourceService.save(resource1_3_2);
                    role1ResourceList.add(resource1_3_2);

                    Resource resource1_3_3 = new Resource();
                    resource1_3_3.setIdentifier("sys:resource:update");
                    resource1_3_3.setName("资源修改");
                    resource1_3_3.setUrl("/resource/update/**");
                    resource1_3_3.setValid(EnableDisableStatus.ENABLE);
                    resource1_3_3.setTreeLevel(2);
                    resource1_3_3.setEnd(true);
                    resource1_3_3.setParent(resource1_3);
                    resource1_3_3.setPriority(0);
                    resource1_3_3.setType(ResourceType.FUNCTION);
                    resource1_3_3 = resourceService.save(resource1_3_3);
                    role1ResourceList.add(resource1_3_3);

                    Resource resource1_3_4 = new Resource();
                    resource1_3_4.setIdentifier("sys:resource:view");
                    resource1_3_4.setName("资源查看");
                    resource1_3_4.setUrl("/resource/view/**");
                    resource1_3_4.setValid(EnableDisableStatus.ENABLE);
                    resource1_3_4.setTreeLevel(2);
                    resource1_3_4.setEnd(true);
                    resource1_3_4.setParent(resource1_3);
                    resource1_3_4.setPriority(0);
                    resource1_3_4.setType(ResourceType.FUNCTION);
                    resource1_3_4 = resourceService.save(resource1_3_4);
                    role1ResourceList.add(resource1_3_4);

                    Resource resource1_3_5 = new Resource();
                    resource1_3_5.setIdentifier("sys:resource:delete");
                    resource1_3_5.setName("资源删除");
                    resource1_3_5.setUrl("/resource/delete/**");
                    resource1_3_5.setValid(EnableDisableStatus.ENABLE);
                    resource1_3_5.setTreeLevel(2);
                    resource1_3_5.setEnd(true);
                    resource1_3_5.setParent(resource1_3);
                    resource1_3_5.setPriority(0);
                    resource1_3_5.setType(ResourceType.FUNCTION);
                    resource1_3_5 = resourceService.save(resource1_3_5);
                    role1ResourceList.add(resource1_3_5);
                }

                Resource resource1_4 = new Resource();
//              resource1_4.setIdentifier("");
                resource1_4.setName("日志管理");
                resource1_4.setUrl("/log/list");
                resource1_4.setValid(EnableDisableStatus.ENABLE);
                resource1_4.setTreeLevel(1);
                resource1_4.setEnd(false);
                resource1_4.setParent(resource1);
                resource1_4.setPriority(0);
                resource1_4.setType(ResourceType.MENU);
                resource1_4 = resourceService.save(resource1_4);
                role1ResourceList.add(resource1_4);
                {
                    Resource resource1_4_1 = new Resource();
                    resource1_4_1.setIdentifier("sys:log:list");
                    resource1_4_1.setName("日志列表");
                    resource1_4_1.setUrl("/log/list");
                    resource1_4_1.setValid(EnableDisableStatus.ENABLE);
                    resource1_4_1.setTreeLevel(2);
                    resource1_4_1.setEnd(true);
                    resource1_4_1.setParent(resource1_4);
                    resource1_4_1.setPriority(0);
                    resource1_4_1.setType(ResourceType.FUNCTION);
                    resource1_4_1 = resourceService.save(resource1_4_1);
                    role1ResourceList.add(resource1_4_1);

                    Resource resource1_4_2 = new Resource();
                    resource1_4_2.setIdentifier("sys:log:view");
                    resource1_4_2.setName("日志查看");
                    resource1_4_2.setUrl("/log/view/**");
                    resource1_4_2.setValid(EnableDisableStatus.ENABLE);
                    resource1_4_2.setTreeLevel(2);
                    resource1_4_2.setEnd(true);
                    resource1_4_2.setParent(resource1_4);
                    resource1_4_2.setPriority(0);
                    resource1_4_2.setType(ResourceType.FUNCTION);
                    resource1_4_2 = resourceService.save(resource1_4_2);
                    role1ResourceList.add(resource1_4_2);
                }
            }

            {

                Resource resource2 = new Resource();
//            resource2.setIdentifier("");
                resource2.setName("个人中心");
//            resource2.setUrl("");
                resource2.setValid(EnableDisableStatus.ENABLE);
                resource2.setTreeLevel(0);
                resource2.setEnd(false);
                resource2.setParent(null);
                resource2.setPriority(0);
                resource2.setType(ResourceType.MENU);
                resource2 = resourceService.save(resource2);
                role1ResourceList.add(resource2);
                role2ResourceList.add(resource2);

                Resource resource2_1 = new Resource();
//              resource2_1.setIdentifier("");
                resource2_1.setName("个人信息");
                resource2_1.setUrl("/profile/info");
                resource2_1.setValid(EnableDisableStatus.ENABLE);
                resource2_1.setTreeLevel(1);
                resource2_1.setEnd(false);
                resource2_1.setParent(resource2);
                resource2_1.setPriority(0);
                resource2_1.setType(ResourceType.MENU);
                resource2_1 = resourceService.save(resource2_1);
                role1ResourceList.add(resource2_1);
                role2ResourceList.add(resource2_1);
                {
                    Resource resource2_1_1 = new Resource();
                    resource2_1_1.setIdentifier("user:profile:view");
                    resource2_1_1.setName("个人信息查看");
                    resource2_1_1.setUrl("/profile/info");
                    resource2_1_1.setValid(EnableDisableStatus.ENABLE);
                    resource2_1_1.setTreeLevel(2);
                    resource2_1_1.setEnd(true);
                    resource2_1_1.setParent(resource2_1);
                    resource2_1_1.setPriority(0);
                    resource2_1_1.setType(ResourceType.FUNCTION);
                    resource2_1_1 = resourceService.save(resource2_1_1);
                    role1ResourceList.add(resource2_1_1);
                    role2ResourceList.add(resource2_1_1);
                }

                Resource resource2_2 = new Resource();
//              resource2_2.setIdentifier("");
                resource2_2.setName("更改密码");
                resource2_2.setUrl("/profile/password/change");
                resource2_2.setValid(EnableDisableStatus.ENABLE);
                resource2_2.setTreeLevel(1);
                resource2_2.setEnd(false);
                resource2_2.setParent(resource2);
                resource2_2.setPriority(0);
                resource2_2.setType(ResourceType.MENU);
                resource2_2 = resourceService.save(resource2_2);
                role1ResourceList.add(resource2_2);
                role2ResourceList.add(resource2_2);
                {
                    Resource resource2_2_1 = new Resource();
                    resource2_2_1.setIdentifier("user:profile:changePwd");
                    resource2_2_1.setName("更改密码");
                    resource2_2_1.setUrl("/profile/password/change");
                    resource2_2_1.setValid(EnableDisableStatus.ENABLE);
                    resource2_2_1.setTreeLevel(2);
                    resource2_2_1.setEnd(true);
                    resource2_2_1.setParent(resource2_2);
                    resource2_2_1.setPriority(0);
                    resource2_2_1.setType(ResourceType.FUNCTION);
                    resource2_2_1 = resourceService.save(resource2_2_1);
                    role1ResourceList.add(resource2_2_1);
                    role2ResourceList.add(resource2_2_1);
                }
            }

            Role role1 = new Role();
            role1.setIdentifier("admin");
            role1.setName("系统管理员");
            role1.setIsDefault(YesNoStatus.NO);
            role1.setValid(EnableDisableStatus.ENABLE);
            role1.setResources(role1ResourceList);
            role1 = roleService.save(role1);

            Role role2 = new Role();
            role2.setIdentifier("user");
            role2.setName("普通用户");
            role2.setIsDefault(YesNoStatus.YES);
            role2.setValid(EnableDisableStatus.ENABLE);
            role2.setResources(role2ResourceList);
            role2 = roleService.save(role2);

            List<Role> roleList = new ArrayList<>();
            roleList.add(role1);
            roleList.add(role2);

            User user = new User();
            user.setUsername("qatang");
            user.setPassword("qatang112244");
            user.setRoot(YesNoStatus.YES);
            user.setEmail("qatang@gmail.com");
            user.setMobile("13810122553");
            user.setValid(EnableDisableStatus.ENABLE);
            user.setEmailValid(YesNoStatus.YES);
            user.setMobileValid(YesNoStatus.YES);
            user.setRoles(roleList);

            passwordHelper.encryptPassword(user);
            userService.save(user);
        }
    }
}
