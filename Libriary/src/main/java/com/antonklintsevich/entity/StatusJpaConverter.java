package com.antonklintsevich.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusJpaConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null) {
            return null;
        }
        return userStatus.toString();
    }

    @Override
    public UserStatus convertToEntityAttribute(String string) {
        if (string == null) {
            return null;
        }
        try {
            return UserStatus.valueOf(string);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}