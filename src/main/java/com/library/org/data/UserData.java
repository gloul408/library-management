package com.library.org.data;

import java.util.List;

import com.library.org.enums.Role;

public class UserData {

  private Long id;
  private String name;
  private String email;
  private String phone;
  private List<String> booksIssued;
  private List<Role> roles;

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<String> getBooksIssued() {
    return booksIssued;
  }

  public void setBooksIssued(List<String> booksIssued) {
    this.booksIssued = booksIssued;
  }
}
