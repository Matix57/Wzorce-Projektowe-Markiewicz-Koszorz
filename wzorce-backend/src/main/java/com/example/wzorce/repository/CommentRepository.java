package com.example.wzorce.repository;

import com.example.wzorce.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}