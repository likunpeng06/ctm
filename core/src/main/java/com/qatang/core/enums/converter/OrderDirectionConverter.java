package com.qatang.core.enums.converter;

import com.qatang.core.enums.OrderDirection;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.AttributeConverter;

/**
 * @author qatang
 * @since 2014-12-19 14:56
 */
public class OrderDirectionConverter implements AttributeConverter<OrderDirection, Integer>, Converter<String, OrderDirection> {
    @Override
    public Integer convertToDatabaseColumn(OrderDirection orderDirection) {
        return orderDirection.getValue();
    }

    @Override
    public OrderDirection convertToEntityAttribute(Integer integer) {
        return OrderDirection.get(integer);
    }

    @Override
    public OrderDirection convert(String s) {
        return OrderDirection.get(Integer.parseInt(s));
    }
}
