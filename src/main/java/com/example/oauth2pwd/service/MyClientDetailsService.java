package com.example.oauth2pwd.service;

import com.example.oauth2pwd.model.Client;
import com.example.oauth2pwd.model.MyClientPrincipal;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class MyClientDetailsService implements ClientDetailsService {


  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    //3	600000	ROLE_KG	password,refresh_token	cmb_kg	{bcrypt}$2a$10$COmo8Sf6R1f4R6Yilhf5ruOEzyNLY.XDr3Ga55zbAjaennLKkAlBy	600000	private-fund-app-secret,kg-platform-api,auth-resource,kg-pipeline-engine	read,write	1	1
    Client client = this.findById(clientId);
    if (client == null) {
      throw new ClientRegistrationException(clientId);
    }
    return new MyClientPrincipal(client);
  }

  public Client findById(String clientId) {
    Client client = new Client();
    client.setClientId(clientId);
    client.setAuthorizedGrantTypes("password,refresh_token");
    client.setAccessTokenValidity(3600 * 10);
    client.setClientSecret("{bcrypt}$2a$10$COmo8Sf6R1f4R6Yilhf5ruOEzyNLY.XDr3Ga55zbAjaennLKkAlBy");
    client.setRefreshTokenValidity(3600 * 20);
    client.setResourceIds("auth-resource");
    client.setScope("read,write");
    client.setScopeRequire(true);
    client.setSecretRequire(true);
    return client;
  }
}
