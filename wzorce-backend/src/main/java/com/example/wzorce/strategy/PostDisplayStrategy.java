package com.example.wzorce.strategy;


import com.example.wzorce.dto.PostDto;

public interface PostDisplayStrategy {
    /**
     * Strategia wyświetlania postu.
     * @param postDto Obiekt DTO zawierający dane postu.
     * @return Przetworzony post zgodnie z wybraną strategią.
     */
    PostDto displayPost(PostDto postDto);
}
