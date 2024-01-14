package com.library.org.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.library.org.data.AuthorData;
import com.library.org.entities.AuthorEntity;
import com.library.org.entities.BookEntity;

@Mapper
public interface AuthorMapper {

  @Mapping(target ="id", source = "author.id")
  @Mapping(target ="name", source = "author.name")
  @Mapping(target ="books", expression = "java(toAuthorBooks(author))")
  AuthorData mapAuthor(AuthorEntity author);

  @Named("toAuthorBooks")
  default Set<String> toAuthorBooks(AuthorEntity author){
  return author.getBooks().stream().map(book -> book.getName()).collect(Collectors.toSet());

  }
}
