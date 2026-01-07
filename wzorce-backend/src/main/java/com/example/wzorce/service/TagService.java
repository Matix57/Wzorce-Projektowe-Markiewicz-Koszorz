package com.example.wzorce.service;

import com.example.wzorce.dto.TagDto;
import com.example.wzorce.Mapper;
import com.example.wzorce.model.Tag;
import com.example.wzorce.repository.PostRepository;
import com.example.wzorce.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;
    private final Mapper mapper;

    public List<TagDto> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(mapper::mapToTagDto)
                .collect(Collectors.toList());
    }

    public TagDto addTag(TagDto tagDto) {
        Tag tag = Tag.builder()
                .name(tagDto.getName())
                .build();
        return mapper.mapToTagDto(tagRepository.save(tag));
    }

    public void addTagsToPost(Long postId, List<String> tagNames) {
        postRepository.findById(postId).ifPresent(post -> {
            List<String> safeTagNames = (tagNames != null) ? tagNames : new ArrayList<>();

            List<Tag> tags = safeTagNames.stream()
                    .map(tagName -> tagRepository.findByName(tagName)
                            .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build())))
                    .collect(Collectors.toList());

            post.setTags(tags);
            postRepository.save(post);
        });
    }

    public void removeTagsByPost(Long postId) {
        postRepository.findById(postId).ifPresent(post -> {
            post.setTags(List.of());
            postRepository.save(post);
        });
    }
}