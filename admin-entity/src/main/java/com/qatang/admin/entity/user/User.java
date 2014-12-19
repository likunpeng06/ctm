package com.qatang.admin.entity.user;

import com.qatang.core.enums.EnableDisableStatus;
import com.qatang.core.entity.AbstractEntity;
import com.qatang.core.enums.YesNoStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qatang
 * @since 2014-12-19 15:01
 */
@Entity
@Table(name = "a_user")
@DynamicInsert
@DynamicUpdate
public class User extends AbstractEntity {
    @Transient
    private static final long serialVersionUID = 1494723713506838837L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;
    @Enumerated
    @Convert(converter = EnableDisableStatus.class)
    private EnableDisableStatus valid;
    @Enumerated
    @Convert(converter = YesNoStatus.class)
    @Column(name = "email_valid")
    private YesNoStatus emailValid;
    @Enumerated
    @Convert(converter = YesNoStatus.class)
    @Column(name = "mobile_valid")
    private YesNoStatus mobileValid;

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
        if (createdTime == null) {
            createdTime = new Date();
        }
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
}
