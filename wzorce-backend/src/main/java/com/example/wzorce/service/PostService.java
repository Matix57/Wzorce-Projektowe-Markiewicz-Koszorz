package com.example.wzorce.service;

import com.example.wzorce.dto.PostDto;
import com.example.wzorce.Mapper;
import com.example.wzorce.model.Post;
import com.example.wzorce.model.Tag;
import com.example.wzorce.repository.PostRepository;
import com.example.wzorce.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final Mapper mapper;

    public PostDto createPost(PostDto postDto) {
        List<Tag> tags = postDto.getTags().stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build())))
                .collect(Collectors.toList());

        Post post = mapper.mapToPostEntity(postDto);
        post.setTags(tags);
        post.setComments(new ArrayList<>());

        post = postRepository.save(post);
        return mapper.mapToPostDto(post);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(mapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    public PostDto getPostById(Long id) {
        return postRepository.findById(id)
                .map(mapper::mapToPostDto)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public PostDto updatePost(Long id, PostDto updatedPostDto) {
        Optional<Post> existingPostOptional = postRepository.findById(id);
        if (existingPostOptional.isPresent()) {
            Post existingPost = existingPostOptional.get();

            existingPost.setTitle(updatedPostDto.getTitle());
            existingPost.setContent(updatedPostDto.getContent());

            List<Tag> updatedTags = updatedPostDto.getTags().stream()
                    .map(tagName -> tagRepository.findByName(tagName)
                            .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build())))
                    .collect(Collectors.toList());
            existingPost.setTags(updatedTags);

            existingPost = postRepository.save(existingPost);

            return mapper.mapToPostDto(existingPost);
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}