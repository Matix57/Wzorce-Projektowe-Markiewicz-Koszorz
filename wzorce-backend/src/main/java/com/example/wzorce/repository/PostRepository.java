package com.example.wzorce.repository;

import com.example.wzorce.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.tags WHERE p.id = :id")
    Optional<Post> findByIdWithTags(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.tags")
    List<Post> findAllWithTags();
}