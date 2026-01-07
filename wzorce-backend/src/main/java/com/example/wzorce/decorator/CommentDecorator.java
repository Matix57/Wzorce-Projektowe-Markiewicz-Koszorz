package com.example.wzorce.decorator;

import com.example.wzorce.dto.PostDto;

/**
 * Dekorator odpowiedzialny za obsługę komentarzy.
 */
public class CommentDecorator extends PostDecorator {

    public CommentDecorator(PostComponent postComponent) {
        super(postComponent);
    }

    @Override
    public PostDto getPost() {
        PostDto postDto = super.getPost();
        postDto.setComments(postDto.getComments());
        return postDto;
    }
}