package com.example.oauth2pwd.common;

import javax.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

public class UserRoleConverter implements AttributeConverter<UserRole, String> {

  @Override
  public String convertToDatabaseColumn(UserRole userRole) {
    if (userRole == null) {
      return null;
    }
    return userRole.name();
  }

  @Override
  public UserRole convertToEntityAttribute(String s) {
    if (StringUtils.isEmpty(s)) {
      return null;
    }
    return UserRole.valueOf(s);
  }
}