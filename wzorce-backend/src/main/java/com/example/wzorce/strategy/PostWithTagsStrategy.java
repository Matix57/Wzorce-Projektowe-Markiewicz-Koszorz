package com.example.wzorce.strategy;

import com.example.wzorce.dto.PostDto;

public class PostWithTagsStrategy implements PostDisplayStrategy {

    @Override
    public PostDto displayPost(PostDto postDto) {
        postDto.setComments(null);
        return postDto;
    }
}
