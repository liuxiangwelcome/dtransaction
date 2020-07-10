package com.yqkj.components.dtransaction.pojo;

import com.yqkj.components.dtransaction.enums.StepStatus;

import javax.persistence.AttributeConverter;

public class StepStatus2DB implements AttributeConverter<StepStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StepStatus stepStatus) {
        return stepStatus.getCode();
    }

    @Override
    public StepStatus convertToEntityAttribute(Integer code) {
        return StepStatus.fromValue(code);
    }
}
