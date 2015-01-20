package com.qatang.admin.entity.role;

import com.qatang.core.entity.AbstractEntity;
import com.qatang.core.enums.EnableDisableStatus;
import com.qatang.core.enums.YesNoStatus;
import com.qatang.core.enums.converter.EnableDisableStatusConverter;
import com.qatang.core.enums.converter.YesNoStatusConverter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author qatang
 * @since 2015-01-20 09:52
 */
@Entity
@Table(name = "a_role", indexes = {
        @Index(name = "uk_identifier", columnList = "identifier", unique = true),
        @Index(name = "idx_created_time", columnList = "created_time")
})
@DynamicInsert
@DynamicUpdate
public class Role extends AbstractEntity {
    public static final int MIN_LENGTH = 1;
    public static final int MAX_LENGTH = 32;
    @Transient
    private static final long serialVersionUID = 2497603644363104035L;

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "{role.identifier.invalid.length}")
    @Column(nullable = false)
    private String identifier;
    @Size(min = MIN_LENGTH, max = MAX_LENGTH, message = "{role.name.invalid.length}")
    @Column(nullable = false)
    private String name;
    @Size(max = 512, message = "{role.description.invalid.length}")
    private String description;

    @Convert(converter = YesNoStatusConverter.class)
    @Column(name = "is_default", nullable = false)
    private YesNoStatus isDefault = YesNoStatus.NO;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.DISABLE;

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
}
