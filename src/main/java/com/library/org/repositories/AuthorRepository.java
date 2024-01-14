package com.library.org.repositories;

import org.springframework.data.repository.CrudRepository;

import com.library.org.entities.AuthorEntity;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

}
