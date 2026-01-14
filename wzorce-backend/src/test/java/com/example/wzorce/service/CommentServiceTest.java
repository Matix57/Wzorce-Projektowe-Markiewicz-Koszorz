package com.example.wzorce.service;

import com.example.wzorce.Mapper;
import com.example.wzorce.dto.CommentDto;
import com.example.wzorce.model.Comment;
import com.example.wzorce.model.Post;
import com.example.wzorce.repository.CommentRepository;
import com.example.wzorce.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private CommentService commentService;

    public CommentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment_Success() {
        Long postId = 1L;
        CommentDto commentDto = CommentDto.builder()
                .author("User")
                .content("Great post!")
                .build();
        Post post = Post.builder().id(postId).build();
        Comment comment = Comment.builder()
                .author("User")
                .content("Great post!")
                .post(post)
                .build();
        CommentDto savedCommentDto = CommentDto.builder()
                .id(1L)
                .author("User")
                .content("Great post!")
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(mapper.mapToCommentDto(any(Comment.class))).thenReturn(savedCommentDto);

        Optional<CommentDto> result = commentService.addComment(postId, commentDto);

        assertTrue(result.isPresent());
        assertEquals("Great post!", result.get().getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testGetCommentsByPostId_Success() {
        Long postId = 1L;
        Comment comment = Comment.builder().author("User").content("Nice!").build();
        CommentDto commentDto = CommentDto.builder().author("User").content("Nice!").build();

        when(commentRepository.findByPostId(postId)).thenReturn(List.of(comment));
        when(mapper.mapToCommentDto(comment)).thenReturn(commentDto);

        List<CommentDto> result = commentService.getCommentsByPostId(postId);

        assertEquals(1, result.size());
        assertEquals("Nice!", result.get(0).getContent());
        verify(commentRepository, times(1)).findByPostId(postId);
    }

    @Test
    void testDeleteComment_Success() {
        Long commentId = 1L;

        when(commentRepository.existsById(commentId)).thenReturn(true);

        boolean result = commentService.deleteComment(commentId);

        assertTrue(result);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void testDeleteComment_NotFound() {
        Long commentId = 1L;

        when(commentRepository.existsById(commentId)).thenReturn(false);

        boolean result = commentService.deleteComment(commentId);

        assertFalse(result);
        verify(commentRepository, never()).deleteById(commentId);
    }
}