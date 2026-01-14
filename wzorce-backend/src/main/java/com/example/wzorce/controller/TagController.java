package com.example.wzorce.controller;

import com.example.wzorce.dto.TagDto;
import com.example.wzorce.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<TagDto> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<TagDto> addTag(@RequestBody TagDto tagDto) {
        TagDto createdTag = tagService.addTag(tagDto);
        return ResponseEntity.status(201).body(createdTag);
    }
}
