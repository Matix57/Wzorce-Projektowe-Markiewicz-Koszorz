package com.example.wzorce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private List<CommentDto> comments;
}
