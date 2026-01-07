package com.example.wzorce.decorator;

import com.example.wzorce.dto.PostDto;

/**
 * Podstawowa implementacja komponentu Post.
 */
public class BasicPost implements PostComponent {

    private final PostDto postDto;

    public BasicPost(PostDto postDto) {
        this.postDto = postDto;
    }

    @Override
    public PostDto getPost() {
        return postDto;
    }
}