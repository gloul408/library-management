package com.library.org.services;

import java.util.List;

import reactor.core.publisher.Mono;

import com.library.org.data.AuthorData;
import com.library.org.data.BookData;
import com.library.org.requests.BookRequest;

public interface LibraryService {

  Mono<BookData> addBook(BookRequest bookRequest);

  Mono<List<BookData>> getBooks();

  Mono<BookData> getBookById(String id);

  Mono<AuthorData> getBooksByAuthorId(String authorId);

}
