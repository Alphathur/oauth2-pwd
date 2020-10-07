package com.example.oauth2pwd.common.auth;

import com.example.oauth2pwd.service.MyClientDetailsService;
import com.example.oauth2pwd.service.MyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final MyUserDetailService myUserDetailService;
  private final AuthenticationManager authenticationManager;
  private final MyClientDetailsService clientDetailsService;
//  @Autowired
//  private RedisConnectionFactory connectionFactory;


  @Autowired
  public AuthorizationServerConfig(MyUserDetailService myUserDetailService,
      AuthenticationManager authenticationManager, MyClientDetailsService clientDetailsService) {
    this.myUserDetailService = myUserDetailService;
    this.authenticationManager = authenticationManager;
    this.clientDetailsService = clientDetailsService;
  }

  /**
   * 客户端访问配置
   *
   * @param endpoints 用来配置令牌端点的安全约束.
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

    /* 注册授权管理器 */
    endpoints.authenticationManager(authenticationManager);
    /* 注册凭证管理器 */
    endpoints.tokenStore(tokenStore());

    /* 添加增强版token转换器 */
    endpoints.tokenEnhancer(accessTokenConverter());

    /* 配置用户信息管理服务 */
    endpoints.userDetailsService(myUserDetailService);

    DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints
        .getDefaultAuthorizationServerTokenServices();
    tokenServices.setTokenStore(tokenStore());
    /* 开启刷新凭证 */
    tokenServices.setSupportRefreshToken(true);
    /* 只允许一个刷新凭证可用 */
    tokenServices.setReuseRefreshToken(false);
    /* 配置client信息管理服务 */
    tokenServices.setClientDetailsService(clientDetailsService);
    /* 配置token管理服务 */
    endpoints.tokenServices(tokenServices);

    super.configure(endpoints);
  }

  /**
   * 配置客户端详情
   *
   * @param clients 用来配置客户端详情服务.
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }

  /**
   * 配置AuthorizationServer安全认证的相关信息
   *
   * @param security 用来配置令牌端点的安全约束.
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security.allowFormAuthenticationForClients();
  }

  /**
   * 配置RedisToken的存储方式，这里选用JwtTokenStore
   */
  @Bean
  public TokenStore tokenStore() {
//    return new RedisTokenStore(connectionFactory){
    return new JwtTokenStore (accessTokenConverter()) {
      @Override
      public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        OAuth2Authentication authentication = super.readAuthentication(token);
        //自动续期token start
        if (authentication != null) {
          // 如果token没有失效  更新AccessToken过期时间
          DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) token;
          //重新设置过期时间
          int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
          if (validitySeconds > 0) {
            oAuth2AccessToken
                .setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
          }
          //将重新设置过的过期时间重新存入redis, 此时会覆盖redis中原本的过期时间
          storeAccessToken(token, authentication);
        }
        //end
        //添加额外信息
        authentication.setDetails(token.getAdditionalInformation());
        return authentication;
      }
    };
  }

  protected int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
    if (clientDetailsService != null) {
      ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
      Integer validity = client.getAccessTokenValiditySeconds();
      if (validity != null) {
        return validity;
      }
    }
    int accessTokenValiditySeconds = 60 * 1;
    return accessTokenValiditySeconds;
  }

  /**
   * 增强版Token令牌的解码器
   */
  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {

    //在token中添加额外信息，来增强token存储信息
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {

      @Override
      public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
          OAuth2Authentication authentication) {
        MyUserDetails user = (MyUserDetails) authentication.getUserAuthentication()
            .getPrincipal();

        final Map<String, Object> additionalInformation = new HashMap<>(1);
        additionalInformation.put("ext-info", user.getAdditionalInformation());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

        return super.enhance(accessToken, authentication);
      }
    };

    //重新设置解码器所使用的UserDetailsService
    DefaultAccessTokenConverter converter = (DefaultAccessTokenConverter) accessTokenConverter
        .getAccessTokenConverter();
    converter.setUserTokenConverter(defaultUserAuthenticationConverter());

    //配置证书
    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
        new ClassPathResource("mytest.jks"),
        "mypass".toCharArray()
    );
    accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
    return accessTokenConverter;
  }

  //自定义了token解码器，需要添加如下代码重新setUserDetailsService。否则Authentication对象只会包含username。
  @Bean
  public DefaultUserAuthenticationConverter defaultUserAuthenticationConverter() {
    DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
    defaultUserAuthenticationConverter.setUserDetailsService(myUserDetailService);
    return defaultUserAuthenticationConverter;
  }
}
