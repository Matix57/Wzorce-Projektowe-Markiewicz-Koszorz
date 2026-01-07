package com.example.wzorce.decorator;

import com.example.wzorce.dto.PostDto;

/**
 * Dekorator, który usuwa tagi, jeśli nie są potrzebne.
 */
public class TagDecorator extends PostDecorator {

    public TagDecorator(PostComponent postComponent) {
        super(postComponent);
    }

    @Override
    public PostDto getPost() {
        PostDto postDto = super.getPost();
        postDto.setTags(null);
        return postDto;
    }
}