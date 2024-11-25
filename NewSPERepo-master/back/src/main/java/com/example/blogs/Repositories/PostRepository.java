package com.example.blogs.Repositories;

import com.example.blogs.entities.Category;
import com.example.blogs.entities.Post;
import com.example.blogs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}

