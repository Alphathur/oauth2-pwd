package com.example.oauth2pwd.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * 自定义客户端用户详细信息（用于注入到spring security中）
 *
 * @author Goddy
 * @date Create in 下午12:03 2018/6/25
 * @description
 */
public class MyClientPrincipal implements ClientDetails {

  /**
   * 数据库存储的客户信息
   *
   * @see Client
   */
  private Client client;

  public MyClientPrincipal(Client client) {
    this.client = client;
  }

  /**
   * 客户端id
   */
  @Override
  public String getClientId() {
    return client.getClientId();
  }

  /**
   * 客户端可以访问的资源 id 列表
   */
  @Override
  public Set<String> getResourceIds() {
    //将数据库中保存的资源id，以, 分隔 例如：resource_id_1,resource_id_2
    return new HashSet<>(Arrays.asList(client.getResourceIds().split(",")));
  }

  /**
   * 是否需要凭证（密码）{@link #getClientSecret()}来验证此客户端
   */
  @Override
  public boolean isSecretRequired() {
    return client.getSecretRequire();
  }

  /**
   * 客户端凭证（密码）
   */
  @Override
  public String getClientSecret() {
    return client.getClientSecret();
  }


  /**
   * 此客户端是否仅限于特定范围{@link #getScope()}。如果为false，则认证请求的范围将为被忽略了
   */
  @Override
  public boolean isScoped() {
    return client.getScopeRequire();
  }

  /**
   * 这个客户的范围。如果客户端没有作用域，则为空, 作用域：select,edit
   */
  @Override
  public Set<String> getScope() {
    return new HashSet<>(Arrays.asList(client.getScope().split(",")));
  }

  /**
   * 使用此客户端访问的用户可以使的授权类型。 授权类型： 1、密码模式（password）{@link org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter
   * } 2、授权码模式（authorization_code） {@link  org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter}
   * 3、刷新模式（refresh_token） {@link org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter}
   * 4、客户端模式（client_credentials）{@link org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter}
   */
  @Override
  public Set<String> getAuthorizedGrantTypes() {
    //
    return new HashSet<>(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
  }

  /**
   * 此客户端在“authorization_code”访问授权期间使用的预定义重定向URI。参见OAuth规范， 第4.1.1节。 图谱平台与私募使用密码模式因此 重定向uri 为空
   */
  @Override
  public Set<String> getRegisteredRedirectUri() {
    return null;
  }

  /**
   * 获取客户端的权限
   */
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    //将数据库中保存的权限，以, 分隔 例如：ROLE_KG,ROLE_PF
    Collection<GrantedAuthority> collection = new ArrayList<>();
        /*Arrays.asList(client.getAuthorities().split(Constant.SPLIT_COMMA)).forEach(
                auth -> collection.add((GrantedAuthority) () -> auth)
        );*/
    return collection;
  }

  /**
   * 访问令牌有效期
   */
  @Override
  public Integer getAccessTokenValiditySeconds() {
    return client.getAccessTokenValidity();
  }

  /**
   * 刷新令牌（续约令牌）有效期
   */
  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return client.getRefreshTokenValidity();
  }

  /**
   * 校验客户端是否需要用户批准特定范围。 如果此客户端不需要用户批准，则为true
   */
  @Override
  public boolean isAutoApprove(String scope) {
    return false;
  }

  /**
   * 此客户端的附加信息，vanilla OAuth协议不需要，但可能有用，例如， 用于存储描述性信息。
   */
  @Override
  public Map<String, Object> getAdditionalInformation() {
    return null;
  }
}
