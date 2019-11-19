package com.example.oauth2pwd.model;

import com.example.oauth2pwd.common.UserRole;
import com.example.oauth2pwd.common.UserRoleConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;

  @JsonIgnore
  private String password;

  private String email;

  @Convert(converter = UserRoleConverter.class)
  private UserRole userRole;

  private Date createdAt;

  private Date updatedAt;

  private Date lastLoginAt;

  public User() {
    this.createdAt = new Date();
    this.updatedAt = new Date();
    this.lastLoginAt = new Date();
  }
}
