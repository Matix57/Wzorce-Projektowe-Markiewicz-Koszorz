package com.example.wzorce.decorator;

import com.example.wzorce.dto.PostDto;

/**
 * Abstrakcyjny dekorator dla PostComponent.
 */
public abstract class PostDecorator implements PostComponent {

    protected final PostComponent postComponent;

    public PostDecorator(PostComponent postComponent) {
        this.postComponent = postComponent;
    }

    @Override
    public PostDto getPost() {
        return postComponent.getPost();
    }
}