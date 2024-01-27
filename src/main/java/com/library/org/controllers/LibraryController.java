package com.library.org.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import com.library.org.data.AuthorData;
import com.library.org.data.BookData;
import com.library.org.exceptions.LibraryAPIException;
import com.library.org.requests.BookRequest;
import com.library.org.services.LibraryService;

@RestController
public class LibraryController {

  @Autowired
  private LibraryService libraryService;

  @PutMapping("/add-book")
  public Mono<BookData> addBook(@RequestBody BookRequest bookRequest) {
    return libraryService.addBook(bookRequest).switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Could not add book" +bookRequest.getName()+" to database")));
  }

  @GetMapping("/books")
  public Mono<List<BookData>> getBooks() {
    return libraryService.getBooks().switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Could not get all books from database")));
  }

  @GetMapping("/book-by-id")
  public Mono<BookData> getBookById(@RequestParam("id") String id) {
    return libraryService.getBookById(id).switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Could not get book having id: "+id)));
  }

  @GetMapping("/author-books")
  public Mono<AuthorData> getAuthorBooks(@RequestParam("id") String authorId) {
    return libraryService.getBooksByAuthorId(authorId).switchIfEmpty(Mono.error(new LibraryAPIException(
        HttpStatus.INTERNAL_SERVER_ERROR,"Could not fetch books of author with id: "+authorId)));
  }
}
