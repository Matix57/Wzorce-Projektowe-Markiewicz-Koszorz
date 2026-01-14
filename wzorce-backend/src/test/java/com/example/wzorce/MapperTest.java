package com.example.wzorce;

import com.example.wzorce.dto.CommentDto;
import com.example.wzorce.dto.PostDto;
import com.example.wzorce.dto.TagDto;
import com.example.wzorce.model.Comment;
import com.example.wzorce.model.Post;
import com.example.wzorce.model.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    private final Mapper mapper = new Mapper();

    @Test
    void testMapToPostDto() {
        Post post = Post.builder()
                .id(1L)
                .title("Post Title")
                .content("Post Content")
                .author("Author Name")
                .tags(List.of(Tag.builder().id(1L).name("Java").build(), Tag.builder().id(2L).name("Spring").build()))
                .comments(List.of(Comment.builder().id(101L).author("User1").content("Great post!").build()))
                .build();

        PostDto postDto = mapper.mapToPostDto(post);

        assertEquals(1L, postDto.getId());
        assertEquals("Post Title", postDto.getTitle());
        assertEquals("Post Content", postDto.getContent());
        assertEquals("Author Name", postDto.getAuthor());
        assertEquals(2, postDto.getTags().size());
        assertEquals("Java", postDto.getTags().get(0));
        assertEquals("Spring", postDto.getTags().get(1));
        assertEquals(1, postDto.getComments().size());
        assertEquals(101L, postDto.getComments().get(0).getId());
        assertEquals("User1", postDto.getComments().get(0).getAuthor());
        assertEquals("Great post!", postDto.getComments().get(0).getContent());
    }

    @Test
    void testMapToCommentDto() {
        Comment comment = Comment.builder()
                .id(101L)
                .author("John Doe")
                .content("This is a comment.")
                .build();

        CommentDto commentDto = mapper.mapToCommentDto(comment);

        assertEquals(101L, commentDto.getId());
        assertEquals("John Doe", commentDto.getAuthor());
        assertEquals("This is a comment.", commentDto.getContent());
    }

    @Test
    void testMapToTagDto() {
        Tag tag = Tag.builder()
                .id(1L)
                .name("Java")
                .build();

        TagDto tagDto = mapper.mapToTagDto(tag);

        assertEquals(1L, tagDto.getId());
        assertEquals("Java", tagDto.getName());
    }

    @Test
    void testMapToPostEntity() {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("Post Title")
                .content("Post Content")
                .author("Author Name")
                .tags(List.of("Java", "Spring"))
                .build();

        Post post = mapper.mapToPostEntity(postDto);

        assertEquals(1L, post.getId());
        assertEquals("Post Title", post.getTitle());
        assertEquals("Post Content", post.getContent());
        assertEquals("Author Name", post.getAuthor());
        assertNotNull(post.getTags());
        assertEquals(2, post.getTags().size());
        assertEquals("Java", post.getTags().get(0).getName());
        assertEquals("Spring", post.getTags().get(1).getName());
    }
}