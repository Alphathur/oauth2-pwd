package com.example.oauth2pwd.model;

import lombok.Data;

@Data
public class Client {

  private Long id;

  /**
   * 客户端id
   */
  private String clientId;

  /**
   * 客户端可以访问的资源
   */
  private String resourceIds;

  /**
   * 客户端密码（登陆）
   */
  private String clientSecret;

  /**
   * 登陆密码是否验证
   */
  private Boolean secretRequire;

  /**
   * 申请的权限范围 read write
   */
  private String scope;

  /**
   * 权限范围是否需要验证
   */
  private Boolean scopeRequire;

  /**
   * 授权模式
   */
  private String authorizedGrantTypes;

  /**
   * 权限
   */
  private String authorities;

  /**
   * access token (登陆凭证) 有效时间
   */
  private Integer accessTokenValidity;

  /**
   * refresh token (刷新凭证) 有效时间
   */
  private Integer refreshTokenValidity;
}

