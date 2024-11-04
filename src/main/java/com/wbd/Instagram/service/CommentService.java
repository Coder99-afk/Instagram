package com.wbd.Instagram.service;

import com.wbd.Instagram.model.Comment;
import com.wbd.Instagram.model.Post;
import com.wbd.Instagram.model.User;
import com.wbd.Instagram.repository.CommentRepository;
import com.wbd.Instagram.repository.PostRepository;
import com.wbd.Instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Comment addComment(long postId, String content, long userId){
        User user= userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Post post= postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        Comment comment= new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }
    public void deleteComment(Long commentId, User user){
        Comment comment= commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Comment not found"));
        //Checking if user is deleting it's own comment only
        if(!comment.getUser().equals(user)){
            throw  new RuntimeException("You are not authorised to delete this comment");
        }
        commentRepository.delete(comment);
    }
}
