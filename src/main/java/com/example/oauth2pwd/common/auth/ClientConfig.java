package com.example.oauth2pwd.common.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 使用此客户端访问的用户可以使的授权类型。 授权类型： 1、密码模式（password）{@link org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter
 * } 2、授权码模式（authorization_code） {@link  org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter}
 * 3、刷新模式（refresh_token） {@link org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter}
 * 4、客户端模式（client_credentials）{@link org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter}
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "client-config", ignoreInvalidFields = true)
public class ClientConfig {

  private String clientId;
  private String clientSecret;
  private String encodedSecret;
  private String authorizedGrantTypes;
  private String resourceIds;
  private String scope;
  private Boolean scopeRequire;
  private Boolean secretRequire;
  private Integer accessTokenValidity;
  private Integer refreshTokenValidity;
}
