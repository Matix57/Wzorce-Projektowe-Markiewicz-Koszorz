package com.example.wzorce.controller;

import com.example.wzorce.dto.CommentDto;
import com.example.wzorce.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> addComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        Optional<CommentDto> createdComment = commentService.addComment(postId, commentDto);
        return createdComment.map(comment -> ResponseEntity.status(201).body(comment))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        if (commentService.deleteComment(commentId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}