package com.example.wzorce.decorator;

import com.example.wzorce.dto.PostDto;

/**
 * Interfejs bazowy dla komponentu odpowiadającego za post.
 */
public interface PostComponent {
    PostDto getPost();
}