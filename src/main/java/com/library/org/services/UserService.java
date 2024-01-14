package com.library.org.services;

import reactor.core.publisher.Mono;

import com.library.org.data.UserData;
import com.library.org.entities.UserEntity;
import com.library.org.requests.IssueBookRequest;
import com.library.org.requests.UserRequest;

public interface UserService {
   Mono<Void> registerUser(UserRequest userRequest);
   Mono<UserEntity> issueBooks(IssueBookRequest issueBookRequest);
   Mono<UserData> getAllBooksOfUser(String emailId);
}
