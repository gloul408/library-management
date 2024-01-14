package com.library.org.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.library.org.data.AuthorData;
import com.library.org.data.BookData;
import com.library.org.entities.BookEntity;
import com.library.org.requests.BookRequest;

public interface LibraryService {

  Mono<BookData> addBook(BookRequest bookRequest);
  Flux<Iterable<BookData>> getBooks();
  Mono<BookEntity> getBookById(String id);
  Mono<AuthorData> getBooksByAuthorId(String authorId);

}
