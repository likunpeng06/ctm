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
    @Transient
    private static final long serialVersionUID = 1494723713506838837L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 64)
    private String salt;

    @Column(nullable = false, length = 128)
    private String email;

    @Column(length = 32)
    private String mobile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
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
