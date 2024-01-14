package com.library.org.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.library.org.data.AuthorData;
import com.library.org.data.BookData;
import com.library.org.entities.AuthorEntity;
import com.library.org.entities.BookEntity;
import com.library.org.mapper.BookMapper;
import com.library.org.repositories.AuthorRepository;
import com.library.org.repositories.LibraryRepository;
import com.library.org.requests.BookRequest;
import com.library.org.services.LibraryService;

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
    return Mono.just(BookMapper.MAPPER.mapBook(libraryRepository.save(book)));
  }

  @Override
  public Flux<Iterable<BookData>> getBooks() {
    return Flux.just(libraryRepository.findAll().stream().map( bookEntity -> {
      return BookMapper.MAPPER.mapBook(bookEntity);
    }).collect(Collectors.toList()));
  }

  @Override
  public Mono<BookEntity> getBookById(String id) {
    return Mono.just(libraryRepository.findById(Long.parseLong(id)).orElse(null));
  }

  @Override
  public Mono<AuthorData> getBooksByAuthorId(String authorId) {
    return Mono.just(authorRepository.findById(Long.parseLong(authorId)).map( authorEntity -> {
      AuthorData author = new AuthorData();
      author.setName(authorEntity.getName());
      //author.setBooks(authorEntity.getBooks());
      return author;
    }).orElse(null));
  }

}
