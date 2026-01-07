package com.example.wzorce.decorator;

import com.example.wzorce.dto.PostDto;

/**
 * Dekorator odpowiedzialny za obsługę tagów.
 */
public class TagDecorator extends PostDecorator {

    public TagDecorator(PostComponent postComponent) {
        super(postComponent);
    }

    @Override
    public PostDto getPost() {
        PostDto postDto = super.getPost();
        postDto.setTags(postDto.getTags());
        return postDto;
    }
}