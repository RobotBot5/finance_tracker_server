package com.robotbot.finance_tracker_server.domain.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = false)
public class BigDecimalToStringConverter implements AttributeConverter<BigDecimal, String> {

    @Override
    public String convertToDatabaseColumn(BigDecimal attribute) {
        return attribute == null ? null : attribute.toPlainString();
    }

    @Override
    public BigDecimal convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new BigDecimal(dbData);
    }
}
