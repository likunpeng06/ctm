package com.qatang.admin.web.shiro.authentication;

import com.qatang.admin.entity.user.User;
import com.qatang.admin.web.utils.AuthenticatorUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qatang
 * @since 2014-12-22 09:05
 */
@Component
public class PasswordHelper {
    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 1;

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void encryptPassword(User user) {
        String randomSalt = AuthenticatorUtils.generateRandomSaltValue();
        user.setSalt(randomSalt);

        String passwordHashValue = AuthenticatorUtils.generateHashValue(algorithmName, user.getPassword(), this.getSalt(user), hashIterations).toString();//toString == toHex；there is anther method: toBase64
        user.setPassword(passwordHashValue);
    }

    public String getSalt(User user) {
        return user.getUsername() + user.getSalt();
    }
}
