package com.library.org.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.library.org.entities.BookEntity;
import com.library.org.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("SELECT u FROM UserEntity u WHERE u.emailId = :email")
  UserEntity getUserByEmailId(@Param("email") String email);

  @Query("SELECT b FROM BookEntity b JOIN user as u ON u.id = b.user.id WHERE u.emailId = :email")
  List<BookEntity> getAllBooksOfUser(@Param("email") String email);

}
