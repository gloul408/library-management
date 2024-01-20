package com.library.org.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.library.org.auth.AuthenticationManager;
import com.library.org.auth.SecurityContextRepository;
import com.library.org.services.impl.LibraryUserDetailsServiceImpl;


@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private SecurityContextRepository securityContextRepository;
  @Autowired
  private LibraryUserDetailsServiceImpl libraryUserDetailsService;


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
        .csrf(csrf -> csrf.disable())
        .httpBasic(httpBasicSpec -> httpBasicSpec.disable())
        .formLogin(formLoginSpec -> formLoginSpec.disable())
        .authorizeExchange((exchanges) ->
            exchanges
                //URL that starts with /login and /register are accessible
                .pathMatchers("/login").permitAll()
                .pathMatchers("/register").permitAll()
                // any other request requires the user to be authenticated
                .anyExchange().authenticated()
        )
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContextRepository);
    return http.build();
  }

}
