package com.library.org.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import com.library.org.auth.JwtUtil;
import com.library.org.data.BookData;
import com.library.org.data.LoginData;
import com.library.org.data.UserData;
import com.library.org.entities.BookEntity;
import com.library.org.entities.UserEntity;
import com.library.org.mapper.BookMapper;
import com.library.org.mapper.UserMapper;
import com.library.org.repositories.LibraryRepository;
import com.library.org.repositories.UserRepository;
import com.library.org.requests.IssueBookRequest;
import com.library.org.requests.LoginRequest;
import com.library.org.requests.SubmitBookRequest;
import com.library.org.requests.UserRequest;
import com.library.org.services.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private LibraryRepository libraryRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public Mono<Void> registerUser(UserRequest userRequest) {
    UserEntity user = new UserEntity();
    user.setEmailId(userRequest.getEmailId());
    user.setName(userRequest.getName());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    user.setRoles(userRequest.getRoles());
    userRepository.save(user);
    return Mono.empty();
  }

  @Override
  public Mono<UserData> issueBooks(IssueBookRequest issueBookRequest) {
    UserEntity user = userRepository.getUserByEmailId(issueBookRequest.getUserEmailId());
    List<String> bookNames = new ArrayList<>();
    issueBookRequest.getBookIds().stream().forEach(bookId -> {
      BookEntity bookEntity = libraryRepository.getBookById(String.valueOf(bookId));
      if (bookEntity != null) {
        bookEntity.setUser(user);
        bookNames.add(bookEntity.getName());
        libraryRepository.save(bookEntity);
      }
    });
    UserData userData = UserMapper.MAPPER.mapUser(user);
    userData.setBooksIssued(bookNames);
    return Mono.just(userData);
  }

  @Override
  public Mono<List<BookData>> getAllBooksOfUser(String emailId) {
    return Mono.just(
        userRepository.getAllBooksOfUser(emailId).stream().map(BookMapper.MAPPER::mapBook).collect(
            Collectors.toList()));
  }

  @Override
  public Mono<LoginData> loginUser(LoginRequest loginRequest) {
    UserEntity userEntity = userRepository.getUserByEmailId(loginRequest.getEmailId());
    if (userEntity != null) {
      if (passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
        return Mono.just(new LoginData(jwtUtil.generateToken(userEntity),
            "Login success!!"));
      }
    }
    return Mono.just(new LoginData("",
        "User not found or invalid credentials!!"));
  }

  @Override
  public Mono<List<BookData>> submitBook(SubmitBookRequest submitBookRequest) {
    submitBookRequest.getBookIds().stream().forEach(bookId -> {
      BookEntity bookEntity = libraryRepository.getBookById(String.valueOf(bookId));
      if (bookEntity != null) {
        bookEntity.setUser(null);
        libraryRepository.save(bookEntity);
      }
    });
    return Mono.just(userRepository.getAllBooksOfUser(submitBookRequest.getUserEmailId()).stream()
        .map(BookMapper.MAPPER::mapBook).collect(Collectors.toList()));
  }
}
