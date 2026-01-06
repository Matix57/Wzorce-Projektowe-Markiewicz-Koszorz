package com.example.wzorce.service;

import com.example.wzorce.Mapper;
import com.example.wzorce.dto.CommentDto;
import com.example.wzorce.model.Comment;
import com.example.wzorce.repository.CommentRepository;
import com.example.wzorce.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final Mapper mapper;

    public Optional<CommentDto> addComment(Long postId, CommentDto commentDto) {
        return postRepository.findById(postId)
                .map(post -> {
                    Comment comment = Comment.builder()
                            .author(commentDto.getAuthor())
                            .content(commentDto.getContent())
                            .post(post)
                            .build();
                    return mapper.mapToCommentDto(commentRepository.save(comment));
                });
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(mapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    public boolean deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }
}
