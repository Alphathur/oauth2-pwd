package com.example.oauth2pwd.web;

import com.example.oauth2pwd.common.UserRole;
import com.example.oauth2pwd.common.auth.MyUserDetails;
import com.example.oauth2pwd.model.User;
import com.example.oauth2pwd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @PreAuthorize("hasAnyAuthority('ROOT')")
  @PostMapping()
  public User update(User user) {
    Integer userId = user.getId ();
    User dbUser;
    if (userId != null) {
      Optional<User> optionalUser = userRepository.findById ( userId );
      if (!optionalUser.isPresent ()) {
        throw new RuntimeException ( "invalid user id [" + userId + "]" );
      }
      dbUser = optionalUser.get ();
      dbUser.setUpdatedAt ( new Date (  ) );
    } else {
      dbUser = new User ();
    }
    if (!StringUtils.isEmpty ( user.getUsername () )) {
      dbUser.setUsername ( user.getUsername () );
    }
    if (!StringUtils.isEmpty ( user.getEmail () )) {
      dbUser.setEmail ( user.getEmail () );
    }
    if (!StringUtils.isEmpty ( user.getPassword () )) {
      dbUser.setPassword ( passwordEncoder.encode ( user.getPassword () ) );
    }
    userRepository.save ( dbUser );
    return dbUser;
  }

  @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
  @PutMapping("add")
  public User add(String userName) {
    User user = new User();
    user.setUsername(userName);
    user.setPassword(passwordEncoder.encode("123456"));
    user.setUserRole(UserRole.GUEST);
    userRepository.save(user);
    return user;
  }

  @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'DEVELOPER')")
  @DeleteMapping("{id}")
  public String delete(@PathVariable("id") Integer userId) {
    userRepository.deleteById (userId);
    return "Deleted";
  }

  @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'DEVELOPER', 'MEMBER')")
  @GetMapping("{id}")
  public User findOne(@PathVariable("id") Integer userId) {
    return userRepository.findById ( userId ).orElse ( null );
  }

  @PreAuthorize("hasAnyAuthority('ROOT')")
  @GetMapping()
  public List<User> all() {
    return StreamSupport.stream(userRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN', 'DEVELOPER', 'MEMBER', 'GUEST')")
  @GetMapping("login_user")
  public User current(Authentication authentication) {
    MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
    return myUserDetails.getUser();
  }

  @GetMapping("login_id")
  public Integer currentId(User user) {
    return user.getId();
  }
}
