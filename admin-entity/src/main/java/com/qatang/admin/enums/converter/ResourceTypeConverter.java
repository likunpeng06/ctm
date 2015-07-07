package com.qatang.admin.enums.converter;

import com.qatang.admin.enums.ResourceType;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.AttributeConverter;

/**
 * @author qatang
 * @since 2014-12-19 14:56
 */
public class ResourceTypeConverter implements AttributeConverter<ResourceType, Integer>, Converter<String, ResourceType> {
    @Override
    public Integer convertToDatabaseColumn(ResourceType resourceType) {
        return resourceType.getValue();
    }

    @Override
    public ResourceType convertToEntityAttribute(Integer integer) {
        return ResourceType.get(integer);
    }

    @Override
    public ResourceType convert(String s) {
        return ResourceType.get(Integer.parseInt(s));
    }
}
