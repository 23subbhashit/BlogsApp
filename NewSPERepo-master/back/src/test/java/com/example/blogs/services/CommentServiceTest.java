package com.example.blogs.services;

import com.example.blogs.Repositories.CommentRepository;
import com.example.blogs.Repositories.PostRepository;
import com.example.blogs.entities.Comment;
import com.example.blogs.entities.Post;
import com.example.blogs.exceptions.ResourceNotFoundException;
import com.example.blogs.payloads.CommentDto;
import com.example.blogs.services.implementations.CommentImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @InjectMocks
    private CommentImplementation commentService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ModelMapper modelMapper;

    private Post post;
    private Comment comment;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        post = new Post();
        post.setId(1);
        post.setTitle("Test Post");

        comment = new Comment();
        comment.setId(1);
        comment.setContent("Test Comment");
        comment.setPost(post);

        commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setContent("Test Comment");
    }

    @Test
    void testCreateComment() {
        when(postRepository.findById(anyInt())).thenReturn(Optional.of(post));
        when(modelMapper.map(any(CommentDto.class), eq(Comment.class))).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(modelMapper.map(any(Comment.class), eq(CommentDto.class))).thenReturn(commentDto);

        CommentDto createdComment = commentService.createComment(commentDto, 1);

        assertNotNull(createdComment);
        assertEquals(commentDto.getId(), createdComment.getId());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testCreateCommentPostNotFound() {
        when(postRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.createComment(commentDto, 1));
    }

    @Test
    void testDeleteComment() {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(comment));

        commentService.deleteComment(1);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void testDeleteCommentNotFound() {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> commentService.deleteComment(1));
    }
}
