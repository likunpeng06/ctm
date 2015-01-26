package com.qatang.admin.entity.user;

import com.qatang.admin.entity.role.Role;
import com.qatang.core.entity.AbstractEntity;
import com.qatang.core.enums.EnableDisableStatus;
import com.qatang.core.enums.YesNoStatus;
import com.qatang.core.enums.converter.EnableDisableStatusConverter;
import com.qatang.core.enums.converter.YesNoStatusConverter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author qatang
 * @since 2014-12-19 15:01
 */
@Entity
@Table(name = "a_user", indexes = {
        @Index(name = "uk_username", columnList = "username", unique = true),
        @Index(name = "uk_email", columnList = "email", unique = true),
        @Index(name = "idx_created_time", columnList = "created_time"),
        @Index(name = "idx_valid", columnList = "valid"),
        @Index(name = "idx_valid_email_mobile", columnList = "email_valid,mobile_valid")
})
//@FieldMatch(first = "password", second = "conPassword", message = "{password.fields.must.match}")
@DynamicInsert
@DynamicUpdate
public class User extends AbstractEntity {
    public static final String USERNAME_PATTERN = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d_a-zA-Z][\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w]{1,19}$";
    public static final String EMAIL_PATTERN = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?";
    public static final String MOBILE_PHONE_NUMBER_PATTERN = "^0{0,1}(13[0-9]|15[0-9]|14[0-9]|18[0-9])[0-9]{8}$";
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 32;

    @Transient
    private static final long serialVersionUID = 1494723713506838837L;

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = "用户名长度错误！")
    @Pattern(regexp = USERNAME_PATTERN, message = "用户名格式错误！")
    @Column(updatable = false)
    private String username;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "密码长度错误！")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 64)
    private String salt;

    @Pattern(regexp = EMAIL_PATTERN, message = "邮箱格式错误！")
    @Column(nullable = false, length = 128)
    private String email;

//    @Pattern(regexp = MOBILE_PHONE_NUMBER_PATTERN, message = "手机号格式错误！")
    @Column(length = 32)
    private String mobile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    @Convert(converter = YesNoStatusConverter.class)
    @Column(name = "email_valid", nullable = false)
    private YesNoStatus emailValid = YesNoStatus.NO;

    @Convert(converter = YesNoStatusConverter.class)
    @Column(name = "mobile_valid", nullable = false)
    private YesNoStatus mobileValid = YesNoStatus.NO;

    @Convert(converter = YesNoStatusConverter.class)
    @Column(name = "root", nullable = false)
    private YesNoStatus root = YesNoStatus.NO;

    @ManyToMany
    @JoinTable(name = "a_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }

    public YesNoStatus getEmailValid() {
        return emailValid;
    }

    public void setEmailValid(YesNoStatus emailValid) {
        this.emailValid = emailValid;
    }

    public YesNoStatus getMobileValid() {
        return mobileValid;
    }

    public void setMobileValid(YesNoStatus mobileValid) {
        this.mobileValid = mobileValid;
    }

    public void setRoot(YesNoStatus root) {
        this.root = root;
    }

    public YesNoStatus getRoot() {
        return root;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean contains(Role role) {
        return roles != null && roles.contains(role);
    }
}
