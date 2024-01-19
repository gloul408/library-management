package com.library.org.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import com.library.org.data.AuthorData;
import com.library.org.data.BookData;
import com.library.org.entities.AuthorEntity;
import com.library.org.entities.BookEntity;
import com.library.org.mapper.AuthorMapper;
import com.library.org.mapper.BookMapper;
import com.library.org.repositories.AuthorRepository;
import com.library.org.repositories.LibraryRepository;
import com.library.org.requests.BookRequest;
import com.library.org.services.LibraryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class LibraryServiceImpl implements LibraryService {

  @Autowired
  private LibraryRepository libraryRepository;
  @Autowired
  private AuthorRepository authorRepository;

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Mono<BookData> addBook(BookRequest bookRequest) {
    BookEntity book = new BookEntity();
    book.setName(bookRequest.getName());
    Set<AuthorEntity> authorsWithBookInfo = bookRequest.getAuthor().stream().map(authorReq -> {
      AuthorEntity author = new AuthorEntity();
      author.setName(authorReq.getName());
      author.getBooks().addAll(new HashSet<>(Arrays.asList(book)));
      return author;
    }).collect(Collectors.toSet());
    book.setAuthors(authorsWithBookInfo);
    authorRepository.saveAll(authorsWithBookInfo);
    return Mono.just(BookMapper.MAPPER.mapBook(libraryRepository.save(
        book))); //Using ReactiveCrudRepo It will emit value wrapped in Mono/Flux.
  }

  @Override
  public Mono<List<BookData>> getBooks() {
    return Mono.just(libraryRepository.findAll().stream().map(BookMapper.MAPPER::mapBook)
        .collect(Collectors.toList()));
  }

  @Override
  public Mono<BookData> getBookById(String id) {
    return Mono.just(BookMapper.MAPPER.mapBook(
        libraryRepository.findById(Long.parseLong(id)).orElse(new BookEntity())));
  }

  @Override
  public Mono<AuthorData> getBooksByAuthorId(String authorId) {
    return Mono.just(AuthorMapper.MAPPER.mapAuthor(
        authorRepository.findById(Long.parseLong(authorId)).orElse(new AuthorEntity())));
  }
}
