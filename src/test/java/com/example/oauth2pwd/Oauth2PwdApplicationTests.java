package com.example.oauth2pwd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Oauth2PwdApplicationTests {


  @Test
  void contextLoads() {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    boolean f = passwordEncoder.matches("ruyiruyi", "$2a$10$COmo8Sf6R1f4R6Yilhf5ruOEzyNLY.XDr3Ga55zbAjaennLKkAlBy");
    System.out.println(f);
    System.out.println(passwordEncoder.encode("mypass"));

  }

}
