package com.library.org.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.library.org.data.BookData;
import com.library.org.entities.BookEntity;

@Mapper
public interface BookMapper {
  BookMapper MAPPER = Mappers.getMapper(BookMapper.class);

  @Mapping(target= "id", source = "book.id")
  @Mapping(target = "name", source = "book.name")
  @Mapping(target = "authors", expression = "java(toAuthors(book))")
  BookData mapBook(BookEntity book);

  @Named("toAuthors")
  default Set<String> toAuthors(BookEntity book){
    return book.getAuthors().stream().map(authorEntity -> authorEntity.getName()).collect(
        Collectors.toSet());
  }
}
