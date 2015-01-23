package com.qatang.admin.web.shiro.realm;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.admin.entity.role.Role;
import com.qatang.admin.entity.user.User;
import com.qatang.admin.service.user.UserService;
import com.qatang.admin.web.shiro.authentication.PasswordHelper;
import com.qatang.core.enums.EnableDisableStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author qatang
 * @since 2014-12-19 18:26
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();
        user = userService.get(user.getId());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Set<String> stringPermissions = new HashSet<>();
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            List<Role> roles = user.getRoles();
            for (Role role : roles) {
                if (role.getResources() != null && role.getResources().size() > 0) {
                    for (Resource resource : role.getResources()) {
                        if (resource.getValid() == EnableDisableStatus.ENABLE && !StringUtils.isEmpty(resource.getIdentifier())) {
                            stringPermissions.add(resource.getIdentifier());
                        }
                    }
                }
            }
        }

        authorizationInfo.setStringPermissions(stringPermissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();

        User user = userService.findByUsername(username);
        if (user == null) {
            user = userService.findByEmail(username);
        }

        if(user == null) {
            throw new UnknownAccountException("帐号或密码错误！");//没找到帐号
        }

        if(EnableDisableStatus.ENABLE != user.getValid()) {
            throw new LockedAccountException("账号无效！");
        }

//        if(YesNoStatus.YES != user.getEmailValid()) {
//            throw new LockedAccountException("邮箱未激活！");
//        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(passwordHelper.getSalt(user)),
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
