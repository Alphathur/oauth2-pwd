package com.example.oauth2pwd.repository;

import com.example.oauth2pwd.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

  User getByUsername(String username);

  User getTopByUsernameAndPassword(String username, String password);
}
