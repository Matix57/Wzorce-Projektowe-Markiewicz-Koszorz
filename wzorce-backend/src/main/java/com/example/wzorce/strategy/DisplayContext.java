package com.example.wzorce.strategy;

import com.example.wzorce.dto.PostDto;

public class DisplayContext {

    private PostDisplayStrategy strategy;

    public DisplayContext(PostDisplayStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PostDisplayStrategy strategy) {
        this.strategy = strategy;
    }

    public PostDto executeStrategy(PostDto postDto) {
        return strategy.displayPost(postDto);
    }
}
