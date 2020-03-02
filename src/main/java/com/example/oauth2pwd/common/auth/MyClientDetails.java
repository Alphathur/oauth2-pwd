package com.example.oauth2pwd.common.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class MyClientDetails implements ClientDetails {

  private final ClientConfig clientConfig;

  public MyClientDetails(ClientConfig clientConfig) {
    this.clientConfig = clientConfig;
  }

  @Override
  public String getClientId() {
    return clientConfig.getClientId();
  }

  @Override
  public Set<String> getResourceIds() {
    return Arrays.stream(clientConfig.getResourceIds().split(",")).collect(Collectors.toSet());
  }

  @Override
  public boolean isSecretRequired() {
    return clientConfig.getSecretRequire();
  }

  @Override
  public String getClientSecret() {//此处应该是加密后的client_secret
    return "$2a$10$8dUcWeKifzn9D8rpR3bbO./fMtRAm5vd19sTxVM3gCK5ig.djn7ae";
  }

  @Override
  public boolean isScoped() {
    return clientConfig.getScopeRequire();
  }

  @Override
  public Set<String> getScope() {
    return Arrays.stream(clientConfig.getScope().split(",")).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    return Arrays.stream(clientConfig.getAuthorizedGrantTypes().split(",")).collect(Collectors.toSet());
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return Collections.emptySet();
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return clientConfig.getAccessTokenValidity();
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return clientConfig.getAccessTokenValidity();
  }

  @Override
  public boolean isAutoApprove(String s) {
    return false;
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    return Collections.emptyMap();
  }
}
