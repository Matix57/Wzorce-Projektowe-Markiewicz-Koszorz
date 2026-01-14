package com.example.wzorce.service;

import com.example.wzorce.Mapper;
import com.example.wzorce.dto.PostDto;
import com.example.wzorce.model.Post;
import com.example.wzorce.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private PostService postService;

    public PostServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPosts() {
        Post post = Post.builder().id(1L).title("First Post").build();
        PostDto postDto = PostDto.builder().id(1L).title("First Post").build();

        when(postRepository.findAllWithTags()).thenReturn(List.of(post));
        when(mapper.mapToPostDto(post)).thenReturn(postDto);

        List<PostDto> result = postService.getAllPosts();

        assertEquals(1, result.size());
        assertEquals("First Post", result.get(0).getTitle());
        verify(postRepository, times(1)).findAllWithTags();
    }

    @Test
    void testGetPostById_Success() {
        Long postId = 1L;
        Post post = Post.builder().id(postId).title("Post Title").build();
        PostDto postDto = PostDto.builder().id(postId).title("Post Title").build();

        when(postRepository.findByIdWithTags(postId)).thenReturn(Optional.of(post));
        when(mapper.mapToPostDto(post)).thenReturn(postDto);

        PostDto result = postService.getPostById(postId);

        assertEquals("Post Title", result.getTitle());
        verify(postRepository, times(1)).findByIdWithTags(postId);
    }

    @Test
    void testCreatePost() {
        PostDto postDto = PostDto.builder().title("New Post").build();
        Post post = Post.builder().title("New Post").build();

        when(mapper.mapToPostEntity(postDto)).thenReturn(post);

        when(postRepository.save(post)).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1L);
            return savedPost;
        });

        when(postRepository.findByIdWithTags(1L)).thenReturn(Optional.of(post));

        when(mapper.mapToPostDto(post)).thenReturn(
                PostDto.builder().id(1L).title("New Post").build()
        );

        PostDto result = postService.createPost(postDto);

        assertNotNull(result.getId());
        assertEquals("New Post", result.getTitle());
        verify(postRepository, times(1)).save(post);
        verify(postRepository, times(1)).findByIdWithTags(1L);
    }
}