package com.example.oauth2pwd.web;

import com.example.oauth2pwd.model.Client;
import com.example.oauth2pwd.model.MyClientPrincipal;
import com.example.oauth2pwd.repository.UserRepository;
import com.example.oauth2pwd.service.MyClientDetailsService;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oa")
public class OAController {

  private final TokenEndpoint tokenEndpoint;

  private final MyClientDetailsService clientService;

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;


  @Value("${auth2.certPath}")
  private String certPath;
  @Value("${auth2.clientId}")
  private String clientId;
  @Value("${auth2.clientSecret}")
  private String clientSecret;

  @Autowired
  public OAController(TokenEndpoint tokenEndpoint, MyClientDetailsService clientService,
      PasswordEncoder passwordEncoder,
      UserRepository userRepository) {
    this.tokenEndpoint = tokenEndpoint;
    this.clientService = clientService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @PostMapping("/token")
  public ResponseEntity<OAuth2AccessToken> token(@RequestParam String username,
      @RequestParam String password)
      throws Exception {

    Map<String, String> params = new HashMap<>();
    params.put("client_id", clientId);
    params.put("client_secret", clientSecret);
    params.put("grant_type", "password");
    params.put("username", username);
    params.put("password", password);

    Client client = clientService.findById(clientId);
    if (client == null) {
      throw new NoSuchClientException("No such Client!");
    }

    MyClientPrincipal clientPrincipal = new MyClientPrincipal(client);

    Principal principal = new UsernamePasswordAuthenticationToken(
        clientPrincipal.getClientId(),
        clientPrincipal.getClientSecret(),
        clientPrincipal.getAuthorities()
    );

    return tokenEndpoint.postAccessToken(principal, params);
  }
}
