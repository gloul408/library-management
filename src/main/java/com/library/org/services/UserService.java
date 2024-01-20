package com.library.org.services;

import java.util.List;

import reactor.core.publisher.Mono;

import com.library.org.data.BookData;
import com.library.org.data.LoginData;
import com.library.org.data.UserData;
import com.library.org.requests.IssueBookRequest;
import com.library.org.requests.LoginRequest;
import com.library.org.requests.SubmitBookRequest;
import com.library.org.requests.UserRequest;

public interface UserService {

  Mono<Void> registerUser(UserRequest userRequest);

  Mono<UserData> issueBooks(IssueBookRequest issueBookRequest);

  Mono<List<BookData>> getAllBooksOfUser(String emailId);

  Mono<LoginData> loginUser(LoginRequest loginRequest);

  Mono<List<BookData>> submitBook(SubmitBookRequest submitBookRequest);
}
