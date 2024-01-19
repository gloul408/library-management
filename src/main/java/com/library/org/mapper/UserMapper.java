package com.library.org.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.library.org.data.UserData;
import com.library.org.entities.BookEntity;
import com.library.org.entities.UserEntity;

@Mapper
public interface UserMapper {

  UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "id", source = "user.id")
  @Mapping(target = "name", source = "user.name")
  @Mapping(target = "phone", source = "user.phoneNumber")
  @Mapping(target = "email", source = "user.emailId")
  @Mapping(target = "roles", source = "user.roles")
  UserData mapUser(UserEntity user);

  @Named("toBooksIssued")
  default List<String> toBooksIssued(List<BookEntity> books) {
    return books.stream()
        .map(book -> book.getName())
        .collect(Collectors.toList());
  }
}
