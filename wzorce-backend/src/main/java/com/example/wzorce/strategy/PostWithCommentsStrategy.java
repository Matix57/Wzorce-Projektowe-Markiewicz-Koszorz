package com.example.wzorce.strategy;

import com.example.wzorce.dto.PostDto;

public class PostWithCommentsStrategy implements PostDisplayStrategy {

    @Override
    public PostDto displayPost(PostDto postDto) {
        postDto.setTags(null);
        return postDto;
    }
}
