package com.example.wzorce.decorator;

import com.example.wzorce.dto.PostDto;

/**
 * Dekorator, który usuwa komentarze, jeśli nie są potrzebne.
 */
public class CommentDecorator extends PostDecorator {

    public CommentDecorator(PostComponent postComponent) {
        super(postComponent);
    }

    @Override
    public PostDto getPost() {
        PostDto postDto = super.getPost();
        postDto.setComments(null);
        return postDto;
    }
}