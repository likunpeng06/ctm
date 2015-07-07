package com.qatang.admin.entity.log;

import com.qatang.admin.entity.user.User;
import com.qatang.core.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qatang
 * @since 2015-01-26 09:51
 */
@Entity
@Table(name = "a_log", indexes = {
        @Index(name = "idx_created_time", columnList = "created_time"),
        @Index(name = "idx_url", columnList = "url"),
        @Index(name = "idx_params", columnList = "params")
})
public class Log extends AbstractEntity {
    @Transient
    private static final long serialVersionUID = -4805878366904858982L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String url;

    private String params;

    @Column(name = "remote_ip")
    private String remoteIp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
