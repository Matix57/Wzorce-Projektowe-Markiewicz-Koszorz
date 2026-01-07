package com.example.wzorce.service;

import com.example.wzorce.dto.PostDto;
import com.example.wzorce.Mapper;
import com.example.wzorce.model.Post;
import com.example.wzorce.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final Mapper mapper;

    public List<PostDto> getAllPosts() {
        return postRepository.findAllWithTags()
                .stream()
                .map(mapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostDto getPostById(Long id) {
        Post post = postRepository.findByIdWithTags(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapper.mapToPostDto(post);
    }

    public PostDto createPost(PostDto postDto) {
        Post post = mapper.mapToPostEntity(postDto);
        postRepository.save(post);
        return getPostById(post.getId());
    }

    private PostDto mapToPostWithTags(Long postId) {
        Post post = postRepository.findByIdWithTags(postId)
                .orElseThrow(() -> new RuntimeException("Post with tags not found"));
        return mapper.mapToPostDto(post);
    }

    public PostDto updatePost(Long id, PostDto updatedPostDto) {
        Post post = postRepository.findByIdWithTags(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(updatedPostDto.getTitle());
        post.setContent(updatedPostDto.getContent());
        post = postRepository.save(post);
        return mapper.mapToPostDto(post);
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
