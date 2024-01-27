package com.library.org.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "library_book")
public class BookEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE)
  private Integer id;
  @Column(unique = true, nullable = false)
  private String name;
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
  @JsonManagedReference
  Set<AuthorEntity> authors = new HashSet<>();

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<AuthorEntity> getAuthors() {
    return authors;
  }

  public void setAuthors(Set<AuthorEntity> authors) {
    this.authors = authors;
  }
}
