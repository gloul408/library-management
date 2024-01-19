package com.library.org.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import com.library.org.entities.UserEntity;
import com.library.org.repositories.UserRepository;

@Service
public class LibraryUserDetailsServiceImpl implements ReactiveUserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    UserEntity userEntity = userRepository.getUserByEmailId(username);
    return Mono.just(User.builder().username(userEntity.getEmailId())
        .password(userEntity.getPassword()).build());
  }
}
