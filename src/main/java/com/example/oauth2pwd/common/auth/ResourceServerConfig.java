package com.example.oauth2pwd.common.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Value("${auth2.enabled}")
  private boolean auth2Enabled;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources
        /* 注册此服务的resourceId，服务唯一标识 */
        .resourceId("auth-resource")
        /* 注册为无状态服务，不和其他服务耦合 */
        .stateless(true);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    if (auth2Enabled) {
      http
          /* 配置session的创建规则 */
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          .and()
          /* 匹配所有请求 */
          .requestMatchers().anyRequest()
          .and()
          /* 注册路径匹配规则，以及需要登录的角色权限 */
          .authorizeRequests()
          .antMatchers("/oa/**").permitAll()
          .antMatchers("/swagger-resources/**").permitAll()
          .antMatchers("/swagger-resources/**").permitAll()
          .antMatchers("/swagger-ui.html").permitAll()
          .antMatchers("/images/**").permitAll()
          .antMatchers("/webjars/**").permitAll()
          .antMatchers("/v2/api-docs").permitAll()
          .antMatchers("/**").authenticated();
    } else {
      http
          /* 配置session的创建规则 */
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          .and()
          /* 匹配所有请求 */
          .requestMatchers().anyRequest()
          .and()
          /* 注册路径匹配规则，以及需要登录的角色权限 */
          .authorizeRequests()
          .antMatchers("/**").permitAll();
    }
  }
}
