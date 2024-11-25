package com.example.blogs.services.implementations;

import com.example.blogs.Repositories.CommentRepository;
import com.example.blogs.Repositories.PostRepository;
import com.example.blogs.entities.Comment;
import com.example.blogs.exceptions.ResourceNotFoundException;
import com.example.blogs.payloads.CommentDto;
import com.example.blogs.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.blogs.entities.Post;

@Service
public class CommentImplementation implements CommentService {
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id",postId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment=this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id", commentId));
        this.commentRepo.delete(com);
    }
}
