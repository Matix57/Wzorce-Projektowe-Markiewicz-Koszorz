package com.example.wzorce.controller;

import com.example.wzorce.dto.PostDto;
import com.example.wzorce.facade.BlogFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BlogController {

    private final BlogFacade blogFacade;

    /**
     * Pobiera wszystkie posty.
     *
     * @return Lista wszystkich postów
     */
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(blogFacade.getAllPosts());
    }

    /**
     * Pobiera post na podstawie ID z opcjonalnymi dekoratorami.
     *
     * @param id ID posta
     * @param includeTags Czy dołączyć tagi
     * @param includeComments Czy dołączyć komentarze
     * @return PostDto z opcjonalnymi dekoratorami
     */
    @GetMapping("/{id}/decorated")
    public ResponseEntity<PostDto> getPostWithDecorators(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean includeTags,
            @RequestParam(defaultValue = "false") boolean includeComments) {
        try {
            return ResponseEntity.ok(blogFacade.getPostWithDecorators(id, includeTags, includeComments));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Tworzy nowy post z tagami i domyślnymi komentarzami.
     *
     * @param postDto Dane nowego posta
     * @return Utworzony PostDto
     */
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        if (postDto.getTags() == null) {
            postDto.setTags(new ArrayList<>());
        }
        PostDto createdPost = blogFacade.createPostWithTagsAndComments(postDto);
        return ResponseEntity.status(201).body(createdPost);
    }

    /**
     * Aktualizuje istniejący post.
     *
     * @param id ID posta do aktualizacji
     * @param updatedPostDto Zaktualizowane dane posta
     * @return Zaktualizowany PostDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto updatedPostDto) {
        try {
            return ResponseEntity.ok(blogFacade.updatePost(id, updatedPostDto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Usuwa post na podstawie ID.
     *
     * @param id ID posta do usunięcia
     * @return Odpowiedź HTTP z kodem 204 (No Content) lub 404 (Not Found)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (blogFacade.deletePost(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}