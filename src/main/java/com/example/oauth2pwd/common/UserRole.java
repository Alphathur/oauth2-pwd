package com.example.oauth2pwd.common;

public enum UserRole {

  ADMIN(0, "ADMIN", "管理员"),

  DEVELOPER(1, "DEVELOPER", "开发者"),

  MEMBER(2, "MEMBER", "成员"),

  GUEST(3, "GUEST", "游客"),

  ROOT(4, "ROOT", "超级管理员"),
  ;

  private final int code;

  private final String authority;

  private final String description;


  UserRole(int code, String authority, String description) {
    this.code = code;
    this.authority = authority;
    this.description = description;
  }
}
