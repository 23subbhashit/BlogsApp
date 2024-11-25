package com.example.blogs.services;

import com.example.blogs.Repositories.CategoryRepository;
import com.example.blogs.Repositories.PostRepository;
import com.example.blogs.Repositories.UserRepository;
import com.example.blogs.entities.Category;
import com.example.blogs.entities.Post;
import com.example.blogs.entities.User;
import com.example.blogs.exceptions.ResourceNotFoundException;
import com.example.blogs.payloads.PostDto;
import com.example.blogs.services.implementations.PostImplementation;
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

public class PostServiceTest {

    @InjectMocks
    private PostImplementation postService;

    @Mock
    private PostRepository postRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private CategoryRepository categoryRepo;

    @Mock
    private ModelMapper modelMapper;

    private Post post;
    private PostDto postDto;
    private User user;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setName("Test User");

        category = new Category();
        category.setCategoryId(1);
        category.setCategoryTitle("Test Category");

        post = new Post();
        post.setPostId(1);
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(user);
        post.setCategory(category);

        postDto = new PostDto();
        postDto.setPostId(1);
        postDto.setTitle("Test Post");
        postDto.setContent("This is a test post.");
    }

    @Test
    void testCreatePost() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(categoryRepo.findById(anyInt())).thenReturn(Optional.of(category));
        when(modelMapper.map(any(PostDto.class), eq(Post.class))).thenReturn(post);
        when(postRepo.save(any(Post.class))).thenReturn(post);
        when(modelMapper.map(any(Post.class), eq(PostDto.class))).thenReturn(postDto);

        PostDto createdPost = postService.createPost(postDto, 1, 1);

        assertNotNull(createdPost);
        assertEquals(postDto.getPostId(), createdPost.getPostId());
        verify(postRepo, times(1)).save(any(Post.class));
    }

    @Test
    void testCreatePostUserNotFound() {
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.createPost(postDto, 1, 1));
    }

    @Test
    void testDeletePost() {
        when(postRepo.findById(anyInt())).thenReturn(Optional.of(post));
        doNothing().when(postRepo).delete(any(Post.class));

        postService.deletePost(1);

        verify(postRepo, times(1)).delete(post);
    }

    @Test
    void testDeletePostNotFound() {
        when(postRepo.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.deletePost(1));
    }
}
