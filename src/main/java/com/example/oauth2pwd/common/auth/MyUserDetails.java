package com.example.oauth2pwd.common.auth;

import com.example.oauth2pwd.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

  private final User user;
  private final Collection<? extends GrantedAuthority> authorities;

  public MyUserDetails(User user) {
    this.user = user;
    this.authorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList(Objects.requireNonNull(user.getUserRole().name()));
  }

  public User getUser() {
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Map<String, Object> getAdditionalInformation() {
    HashMap additional = new HashMap<String, Object>();
    additional.put("userId", user.getId());
    return additional;
  }
}
