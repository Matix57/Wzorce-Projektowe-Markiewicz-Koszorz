package com.example.wzorce.controller;

import com.example.wzorce.dto.PostDto;
import com.example.wzorce.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BlogController {

    private final PostService postService;

    // Pobierz wszystkie posty
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        try {
            PostDto post = postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Obsługa błędu, jeśli nie znaleziono posta
        }
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        return ResponseEntity.status(201).body(createdPost); // HTTP 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto updatedPostDto) {
        try {
            PostDto updatedPost = postService.updatePost(id, updatedPostDto);
            return ResponseEntity.ok(updatedPost); // HTTP 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Obsługa błędu
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean isDeleted = postService.deletePost(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        }
        return ResponseEntity.notFound().build(); // HTTP 404 Not Found
    }
}