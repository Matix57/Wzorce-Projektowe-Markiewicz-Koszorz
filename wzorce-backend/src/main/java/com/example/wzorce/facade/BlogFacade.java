package com.example.wzorce.facade;

import com.example.wzorce.Mapper;
import com.example.wzorce.decorator.BasicPost;
import com.example.wzorce.decorator.CommentDecorator;
import com.example.wzorce.decorator.PostComponent;
import com.example.wzorce.decorator.TagDecorator;
import com.example.wzorce.dto.PostDto;
import com.example.wzorce.model.Post;
import com.example.wzorce.repository.PostRepository;
import com.example.wzorce.service.CommentService;
import com.example.wzorce.service.PostService;
import com.example.wzorce.service.TagService;
import com.example.wzorce.strategy.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BlogFacade {

    private final PostService postService;
    private final TagService tagService;
    private final CommentService commentService;
    private final Mapper mapper;
    private final PostRepository postRepository;

    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    /**
     * Zwraca post z pełnym widokiem albo za pomocą dostarczonej strategii.
     */
    @Transactional
    public PostDto getPostWithDecorators(Long id, boolean excludeTags, boolean excludeComments) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Hibernate.initialize(post.getTags());
        Hibernate.initialize(post.getComments());

        PostComponent postComponent = new BasicPost(mapper.mapToPostDto(post));

        if (excludeTags) {
            postComponent = new TagDecorator(postComponent);
        }
        if (excludeComments) {
            postComponent = new CommentDecorator(postComponent);
        }

        return postComponent.getPost();
    }

    /**
     * Tworzy post z tagami i domyślnymi komentarzami.
     *
     * @param postDto Obiekt reprezentujący dane nowego posta
     * @return Utworzony PostDto
     */
    public PostDto createPostWithTagsAndComments(PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        List<String> tags = postDto.getTags() != null ? postDto.getTags() : new ArrayList<>();
        tagService.addTagsToPost(createdPost.getId(), tags);
        commentService.addDefaultCommentsToPost(createdPost.getId());
        return createdPost;
    }

    /**
     * Aktualizuje istniejący post na podstawie dostarczonego PostDto.
     *
     * @param id ID posta do aktualizacji
     * @param updatedPostDto Nowe dane posta
     * @return Zaktualizowany PostDto
     */
    public PostDto updatePost(Long id, PostDto updatedPostDto) {
        return postService.updatePost(id, updatedPostDto);
    }

    /**
     * Usuwa post na podstawie jego ID.
     *
     * @param postId ID posta do usunięcia
     * @return True, jeśli post został usunięty, False w przeciwnym razie
     */
    public boolean deletePost(Long postId) {
        postRepository.findById(postId).ifPresent(post -> {
            post.setTags(new ArrayList<>());
            postRepository.save(post);
        });

        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }
}