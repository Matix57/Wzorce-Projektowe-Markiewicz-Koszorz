package com.example.wzorce.repository;

import com.example.wzorce.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Modifying
    @Query("DELETE FROM Tag t WHERE t.posts IS EMPTY")
    void deleteUnusedTags();
}