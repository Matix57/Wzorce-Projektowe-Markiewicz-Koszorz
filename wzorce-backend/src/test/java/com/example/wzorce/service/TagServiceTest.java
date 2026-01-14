package com.example.wzorce.service;

import com.example.wzorce.Mapper;
import com.example.wzorce.dto.TagDto;
import com.example.wzorce.model.Tag;
import com.example.wzorce.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private TagService tagService;

    public TagServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTags() {
        Tag tag = Tag.builder().id(1L).name("Java").build();
        TagDto tagDto = TagDto.builder().id(1L).name("Java").build();

        when(tagRepository.findAll()).thenReturn(List.of(tag));
        when(mapper.mapToTagDto(tag)).thenReturn(tagDto);

        List<TagDto> result = tagService.getAllTags();

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void testAddTag() {
        TagDto tagDto = TagDto.builder().name("Spring").build();
        Tag tag = Tag.builder().name("Spring").build();
        Tag savedTag = Tag.builder().id(1L).name("Spring").build();

        when(tagRepository.save(tag)).thenReturn(savedTag);
        when(mapper.mapToTagDto(savedTag)).thenReturn(
                TagDto.builder().id(1L).name("Spring").build()
        );

        TagDto result = tagService.addTag(tagDto);

        assertNotNull(result.getId());
        assertEquals("Spring", result.getName());
        verify(tagRepository, times(1)).save(tag);
    }
}