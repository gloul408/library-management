package com.library.org.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import com.library.org.data.BookData;
import com.library.org.data.LoginData;
import com.library.org.data.UserData;
import com.library.org.requests.IssueBookRequest;
import com.library.org.requests.LoginRequest;
import com.library.org.requests.SubmitBookRequest;
import com.library.org.requests.UserRequest;
import com.library.org.services.UserService;

@RestController
public class UsersController {

  @Autowired
  private UserService userService;

  @PutMapping(value = "/register")
  public Mono<Void> registerUser(@RequestBody UserRequest userRequest) {
    return userService.registerUser(userRequest);
  }

  @PostMapping(value = "/login")
  public Mono<LoginData> login(@RequestBody LoginRequest loginRequest) {
    return userService.loginUser(loginRequest);
  }

  @PutMapping(value = "/issue-books")
  public Mono<UserData> issueBooks(@RequestBody IssueBookRequest issueBookRequest) {
    return userService.issueBooks(issueBookRequest);
  }

  @GetMapping(value = "/user-books")
  public Mono<List<BookData>> getAllBooksOfUser(@RequestParam("emailId") String emailId) {
    return userService.getAllBooksOfUser(emailId);
  }

  @PostMapping(value = "/submit-books")
  public Mono<List<BookData>> getAllBooksOfUser(@RequestBody SubmitBookRequest submitBookRequest) {
    return userService.submitBook(submitBookRequest);
  }

}
