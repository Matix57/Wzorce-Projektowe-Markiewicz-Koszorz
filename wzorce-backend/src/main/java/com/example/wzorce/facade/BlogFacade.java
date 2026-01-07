package com.example.wzorce.facade;

import com.example.wzorce.Mapper;
import com.example.wzorce.dto.PostDto;
import com.example.wzorce.model.Post;
import com.example.wzorce.repository.PostRepository;
import com.example.wzorce.service.CommentService;
import com.example.wzorce.service.PostService;
import com.example.wzorce.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogFacade {

    private final PostService postService;
    private final TagService tagService;
    private final CommentService commentService;
    private final Mapper mapper;
    private final PostRepository postRepository;

    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @Transactional
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Hibernate.initialize(post.getTags());

        return mapper.mapToPostDto(post);
    }

    public PostDto createPostWithTagsAndComments(PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        List<String> tags = postDto.getTags() != null ? postDto.getTags() : new ArrayList<>();
        tagService.addTagsToPost(createdPost.getId(), tags);
        commentService.addDefaultCommentsToPost(createdPost.getId());
        return createdPost;
    }

    public PostDto updatePost(Long id, PostDto updatedPostDto) {
        return postService.updatePost(id, updatedPostDto);
    }

    public boolean deletePost(Long postId) {
        commentService.deleteCommentsByPost(postId);
        tagService.removeTagsByPost(postId);
        return postService.deletePost(postId);
    }
}
