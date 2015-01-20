package com.qatang.admin.entity.resource;

import com.qatang.admin.enums.ResourceType;
import com.qatang.admin.enums.converter.ResourceTypeConverter;
import com.qatang.core.entity.AbstractEntity;
import com.qatang.core.enums.EnableDisableStatus;
import com.qatang.core.enums.converter.EnableDisableStatusConverter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * @author qatang
 * @since 2014-12-19 15:01
 */
@Entity
@Table(name = "a_resource", indexes = {
        @Index(name = "idx_created_time", columnList = "created_time"),
        @Index(name = "idx_valid", columnList = "valid")
})
@DynamicInsert
@DynamicUpdate
public class Resource extends AbstractEntity {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 32;

    @Transient
    private static final long serialVersionUID = -5486350981177791424L;

    @Id
    @GeneratedValue
    private Long id;

    @Size(max = MAX_LENGTH, message = "{resource.identifier.invalid.length}")
    private String identifier;

    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "{resource.name.invalid.length}")
    @Column(nullable = false)
    private String name;

    @Size(max = 512, message = "{resource.url.invalid.length}")
    private String url;

    @Convert(converter = ResourceTypeConverter.class)
    @Column(name = "type", nullable = false)
    private ResourceType type;

    @Column(name = "tree_level", nullable = false)
    private Integer treeLevel = 0;

    @Column(nullable = false)
    private Integer priority = 0;

    @Column(name = "order_field", nullable = false)
    private String orderField;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Resource parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy("id")
    private Set<Resource> children;

    @Column(name = "is_end", nullable = false)
    private boolean isEnd = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.DISABLE;

    @Size(max = 512, message = "{resource.memo.invalid.length}")
    private String memo;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public Set<Resource> getChildren() {
        return children;
    }

    public void setChildren(Set<Resource> children) {
        this.children = children;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
}
