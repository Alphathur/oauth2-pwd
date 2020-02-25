package com.example.oauth2pwd.service;

import com.example.oauth2pwd.common.auth.ClientConfig;
import com.example.oauth2pwd.common.auth.MyClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class MyClientDetailsService implements ClientDetailsService {

  private final ClientConfig clientConfig;

  public MyClientDetailsService(ClientConfig clientConfig) {
    this.clientConfig = clientConfig;
  }

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    return new MyClientDetails(clientConfig);
  }
}
