package com.yqkj.components.dtransaction.pojo;

import com.yqkj.components.dtransaction.enums.MainStatus;

import javax.persistence.AttributeConverter;

public class MainStatus2DB implements AttributeConverter<MainStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MainStatus mainStatus) {
        return mainStatus.getCode();
    }

    @Override
    public MainStatus convertToEntityAttribute(Integer code) {
        return MainStatus.fromValue(code);
    }
}
