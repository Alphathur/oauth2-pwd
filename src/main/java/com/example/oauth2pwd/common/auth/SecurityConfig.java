package com.example.oauth2pwd.common.auth;

import com.example.oauth2pwd.service.MyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  private final MyUserDetailService userDetailsService;

  @Autowired
  public SecurityConfig(PasswordEncoder passwordEncoder,
      MyUserDetailService userDetailsService) {
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    //不拦截握手请求
    http
        /* 获取请求的 */
        .authorizeRequests()
        /* 允许所有option请求 */
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        /* 其他请求方式要求必须要登录 */
        .anyRequest().authenticated()
        .and()
        /* 允许basic登录 */
        .httpBasic()
        .and()
        /* 允许跨域 */
        .csrf().disable();
  }

  /* 注册bean中自定义的authenticationProvider到 授权管理创建中心 */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  /**
   * 将AuthenticationManager暴露到bean中
   */
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    /* 取消强制凭证变为字符串 */
    authProvider.setForcePrincipalAsString(false);

    /* 配置查询用户的服务 */
    authProvider.setUserDetailsService(userDetailsService);

    /* 配置密码的加密方式 */
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }
}
