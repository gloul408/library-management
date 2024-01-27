package com.library.org.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.library.org.exceptions.LibraryAPIException;
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
    return userService.registerUser(userRequest).switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Error registering user with email Id: "+userRequest.getEmailId())));
  }

  @PostMapping(value = "/login")
  public Mono<LoginData> login(@RequestBody LoginRequest loginRequest) {
    return userService.loginUser(loginRequest).switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Could not authenticate!!")));
  }

  @PutMapping(value = "/issue-books")
  public Mono<UserData> issueBooks(@RequestBody IssueBookRequest issueBookRequest) {
    return userService.issueBooks(issueBookRequest).switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Seems there is some error in issuing book(s) with id(s): "+issueBookRequest.getBookIds())));
  }

  @GetMapping(value = "/user-books")
  public Mono<List<BookData>> getAllBooksOfUser(@RequestParam("emailId") String emailId) {
    return userService.getAllBooksOfUser(emailId).switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Could not fetch books of user with email id: "+emailId)));
  }

  @PostMapping(value = "/submit-books")
  public Mono<List<BookData>> getAllBooksOfUser(@RequestBody SubmitBookRequest submitBookRequest) {
    return userService.submitBook(submitBookRequest).switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Seems there is some issue in submitting book(s) with id(s): "+submitBookRequest.getBookIds())));
  }

}
