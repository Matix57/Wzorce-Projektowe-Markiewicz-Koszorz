package com.example.wzorce.service;

import com.example.wzorce.dto.TagDto;
import com.example.wzorce.Mapper;
import com.example.wzorce.model.Tag;
import com.example.wzorce.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
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
}