package com.example.wzorce.strategy;

import com.example.wzorce.dto.PostDto;

public class PreviewStrategy implements PostDisplayStrategy {

    @Override
    public PostDto displayPost(PostDto postDto) {
        postDto.setContent(postDto.getContent().length() > 100
                ? postDto.getContent().substring(0, 100) + "..."
                : postDto.getContent());
        postDto.setComments(null);
        postDto.setTags(null);
        return postDto;
    }
}