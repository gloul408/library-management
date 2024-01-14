package com.library.org.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.library.org.data.AuthorData;
import com.library.org.data.BookData;
import com.library.org.entities.AuthorEntity;
import com.library.org.entities.BookEntity;
import com.library.org.requests.BookRequest;
import com.library.org.services.LibraryService;

@RestController
public class LibraryController {

  @Autowired
  private LibraryService libraryService;

  @PutMapping("/add-book")
  public Mono<BookData> addBook(@RequestBody BookRequest bookRequest) {
    return libraryService.addBook(bookRequest);
  }

  @GetMapping("/books")
  public Flux<Iterable<BookData>> getBooks() {
    return libraryService.getBooks();
  }

  @GetMapping("/book-by-id")
  public Mono<BookEntity> getBookById(@RequestParam("id") String id) {
    return libraryService.getBookById(id);
  }
  @GetMapping("/author-books")
  public Mono<AuthorData> getAuthorBooks(@RequestParam("id") String authorId) {
    return libraryService.getBooksByAuthorId(authorId);
  }
}
