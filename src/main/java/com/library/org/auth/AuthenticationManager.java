package com.library.org.auth;

import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    String username = jwtUtil.getUsernameFromToken(authToken);
    return Mono.just(jwtUtil.validateToken(authToken))
        .filter(valid -> valid)
        .switchIfEmpty(Mono.empty())
        .map(valid -> {
          Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
          List<String> rolesMap = claims.get("role", List.class);
          return new UsernamePasswordAuthenticationToken(
              username,
              null,
              rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
          );
        });
  }
}
