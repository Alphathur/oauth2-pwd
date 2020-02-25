package com.example.oauth2pwd.web;

import com.example.oauth2pwd.common.auth.ClientConfig;
import com.example.oauth2pwd.common.auth.MyClientDetails;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oa")
public class OAController {

  private final TokenEndpoint tokenEndpoint;
  private final PasswordEncoder passwordEncoder;
  private final ClientConfig clientConfig;

  @Autowired
  public OAController(TokenEndpoint tokenEndpoint,
      PasswordEncoder passwordEncoder,
      ClientConfig clientConfig) {
    this.tokenEndpoint = tokenEndpoint;
    this.passwordEncoder = passwordEncoder;
    this.clientConfig = clientConfig;
  }

  @PostMapping("/token")
  public ResponseEntity<OAuth2AccessToken> token(@RequestParam String username,
      @RequestParam String password)
      throws Exception {

    Map<String, String> params = new HashMap<>();
    params.put("client_id", clientConfig.getClientId());
    params.put("client_secret", clientConfig.getClientSecret());
    params.put("grant_type", "password");
    params.put("username", username);
    params.put("password", password);

    MyClientDetails myClientDetails = new MyClientDetails(clientConfig);
    //用来验证用户是否合法，如果client_secret泄漏，必须重新生产新的client_secret，用户使用新的client_secret来发起授权请求。
    //验证过程非oauth2实现，需自己后端实现
    if(!passwordEncoder.matches(clientConfig.getClientSecret(),myClientDetails.getClientSecret())) {
      throw new RuntimeException("client_secret error");
    }
    Principal principal = new UsernamePasswordAuthenticationToken(
        myClientDetails.getClientId(),
        myClientDetails.getClientSecret(),
        myClientDetails.getAuthorities()
    );

    return tokenEndpoint.postAccessToken(principal, params);
  }
}
