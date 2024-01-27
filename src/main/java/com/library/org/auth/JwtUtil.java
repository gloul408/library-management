package com.library.org.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.library.org.entities.UserEntity;
import com.library.org.exceptions.LibraryAPIException;

@Component
public class JwtUtil {

  private String expirationTime = "900000";

  private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  public Claims getAllClaimsFromToken(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }catch (Exception e) {
     throw new LibraryAPIException(HttpStatus.UNAUTHORIZED, "Unauthorized Access!!");
    }

  }

  public String getUsernameFromToken(String token) {
    return getAllClaimsFromToken(token).getSubject();
  }

  public Date getExpirationDateFromToken(String token) {
    return getAllClaimsFromToken(token).getExpiration();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(UserEntity user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", user.getRoles());
    return doGenerateToken(claims, user.getEmailId());
  }

  private String doGenerateToken(Map<String, Object> claims, String username) {
    Long expirationTimeLong = Long.parseLong(expirationTime); //in second
    final Date createdDate = new Date();
    final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(createdDate)
        .setExpiration(expirationDate)
        .signWith(key)
        .compact();
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

}
