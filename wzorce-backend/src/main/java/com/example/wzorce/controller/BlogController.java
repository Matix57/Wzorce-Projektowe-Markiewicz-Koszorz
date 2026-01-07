package com.example.wzorce.controller;

import com.example.wzorce.dto.PostDto;
import com.example.wzorce.facade.BlogFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BlogController {

    private final BlogFacade blogFacade;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(blogFacade.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(blogFacade.getPostById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        if (postDto.getTags() == null) {
            postDto.setTags(new ArrayList<>());
        }
        PostDto createdPost = blogFacade.createPostWithTagsAndComments(postDto);
        return ResponseEntity.status(201).body(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto updatedPostDto) {
        try {
            return ResponseEntity.ok(blogFacade.updatePost(id, updatedPostDto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (blogFacade.deletePost(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}