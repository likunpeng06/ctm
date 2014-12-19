package com.qatang.core.enums.converter;

import com.qatang.core.enums.EnableDisableStatus;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.AttributeConverter;

/**
 * @author qatang
 * @since 2014-12-19 14:55
 */
public class EnableDisableStatusConverter implements AttributeConverter<EnableDisableStatus, Integer>, Converter<String, EnableDisableStatus> {
    @Override
    public Integer convertToDatabaseColumn(EnableDisableStatus enableDisableStatus) {
        return enableDisableStatus.getValue();
    }

    @Override
    public EnableDisableStatus convertToEntityAttribute(Integer integer) {
        return EnableDisableStatus.get(integer);
    }

    @Override
    public EnableDisableStatus convert(String s) {
        return EnableDisableStatus.get(Integer.parseInt(s));
    }
}
