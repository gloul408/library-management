package com.library.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.org.entities.AuthorEntity;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

}
