package com.library.org.data;

import java.util.Set;

public class AuthorData {

  private Long id;
  private String name;
  private Set<String> books;

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

  public Set<String> getBooks() {
    return books;
  }

  public void setBooks(Set<String> books) {
    this.books = books;
  }
}
