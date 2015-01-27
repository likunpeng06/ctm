package com.qatang.admin.entity.role;

import com.qatang.admin.entity.resource.Resource;
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
 * @since 2015-01-20 09:52
 */
@Entity
@Table(name = "a_role", indexes = {
        @Index(name = "uk_identifier", columnList = "identifier", unique = true),
        @Index(name = "idx_created_time", columnList = "created_time"),
        @Index(name = "idx_is_default", columnList = "is_default")
})
@DynamicInsert
@DynamicUpdate
public class Role extends AbstractEntity {
    @Transient
    private static final long serialVersionUID = 2497603644363104035L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    private String name;

    private String description;

    @Convert(converter = YesNoStatusConverter.class)
    @Column(name = "is_default", nullable = false)
    private YesNoStatus isDefault = YesNoStatus.NO;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @ManyToMany
    @JoinTable(name = "a_role_resource", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private List<Resource> resources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public YesNoStatus getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(YesNoStatus isDefault) {
        this.isDefault = isDefault;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
