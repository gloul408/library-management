package com.library.org.requests;

import java.util.Set;

public class BookRequest {

  private String name;
  private Set<AuthorRequest> author;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<AuthorRequest> getAuthor() {
    return author;
  }

  public void setAuthor(Set<AuthorRequest> author) {
    this.author = author;
  }
}
