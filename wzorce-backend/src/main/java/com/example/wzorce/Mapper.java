package com.example.wzorce;

import com.example.wzorce.dto.CommentDto;
import com.example.wzorce.dto.PostDto;
import com.example.wzorce.dto.TagDto;
import com.example.wzorce.model.Comment;
import com.example.wzorce.model.Post;
import com.example.wzorce.model.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .tags(post.getTags() != null
                        ? post.getTags().stream().map(Tag::getName).collect(Collectors.toList())
                        : new ArrayList<>())
                .comments(post.getComments() != null
                        ? post.getComments().stream().map(this::mapToCommentDto).collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    private List<CommentDto> mapComments(Post post) {
        if (post.getComments() == null) {
            return new ArrayList<>();
        }
        List<CommentDto> comments = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            comments.add(mapToCommentDto(comment));
        }
        return comments;
    }

    public CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .build();
    }

    public TagDto mapToTagDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public Post mapToPostEntity(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(postDto.getAuthor())
                .tags(postDto.getTags() != null
                        ? postDto.getTags().stream()
                        .filter(tagName -> tagName != null && !tagName.isEmpty())
                        .map(tagName -> Tag.builder().name(tagName).build())
                        .toList()
                        : new ArrayList<>())
                .build();
    }
}