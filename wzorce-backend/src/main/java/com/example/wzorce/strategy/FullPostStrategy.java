package com.example.wzorce.strategy;

import com.example.wzorce.dto.PostDto;

public class FullPostStrategy implements PostDisplayStrategy {

    @Override
    public PostDto displayPost(PostDto postDto) {
        return postDto;
    }
}