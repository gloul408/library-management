package com.library.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.library.org.entities.BookEntity;

public interface LibraryRepository extends JpaRepository<BookEntity, Long> {

  @Query("SELECT b FROM BookEntity b WHERE b.name = :name")
  BookEntity getBookByName(@Param("name") String name);

  @Query("SELECT DISTINCT b FROM BookEntity b JOIN b.authors a WHERE b.id = :id")
  BookEntity getBookById(@Param("id") String id);
}
