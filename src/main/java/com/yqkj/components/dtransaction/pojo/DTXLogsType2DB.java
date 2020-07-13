package com.yqkj.components.dtransaction.pojo;

import com.yqkj.components.dtransaction.enums.DTXLogsType;

import javax.persistence.AttributeConverter;

public class DTXLogsType2DB implements AttributeConverter<DTXLogsType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DTXLogsType type) {
        return type.getCode();
    }

    @Override
    public DTXLogsType convertToEntityAttribute(Integer code) {
        return DTXLogsType.fromValue(code);
    }
}
