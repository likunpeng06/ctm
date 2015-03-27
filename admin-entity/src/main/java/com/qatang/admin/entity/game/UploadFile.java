package com.qatang.admin.entity.game;

import com.qatang.core.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qatang
 * @since 2015-01-26 09:51
 */
@Entity
@Table(name = "a_file", indexes = {
        @Index(name = "idx_created_time", columnList = "created_time")
})
public class UploadFile extends AbstractEntity {
    @Transient
    private static final long serialVersionUID = 4223259440016558490L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String url;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
