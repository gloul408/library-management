package com.library.org.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import com.library.org.data.UserData;
import com.library.org.entities.BookEntity;
import com.library.org.entities.UserEntity;
import com.library.org.repositories.LibraryRepository;
import com.library.org.repositories.UserRepository;
import com.library.org.requests.IssueBookRequest;
import com.library.org.requests.UserRequest;
import com.library.org.services.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private LibraryRepository libraryRepository;

  @Override
  public Mono<Void> registerUser(UserRequest userRequest) {
    UserEntity user = new UserEntity();
    user.setEmailId(userRequest.getEmailId());
    user.setName(userRequest.getName());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    userRepository.save(user);
    return Mono.empty();
  }

  @Override
  public Mono<UserEntity> issueBooks(IssueBookRequest issueBookRequest) {
    UserEntity user = userRepository.getUserByEmailId(issueBookRequest.getUserEmailId());
    issueBookRequest.getBookIds().stream().forEach(bookId -> {
      BookEntity book = libraryRepository.getBookById(String.valueOf(bookId));
      book.setUser(user);
      libraryRepository.save(book);
    });
    return Mono.just(user);
  }

  @Override
  public Mono<UserData> getAllBooksOfUser(String emailId) {
    List<BookEntity> booksIssued = userRepository.getAllBooksOfUser(emailId);
    UserData userData = new UserData();
    userData.setBooksIssued(booksIssued.stream().map(book -> book.getName()).collect(Collectors.toList()));
    userData.setEmail(emailId);
    return Mono.just(userData);
  }
}
