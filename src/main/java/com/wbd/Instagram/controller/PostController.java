package com.wbd.Instagram.controller;

import com.wbd.Instagram.model.Comment;
import com.wbd.Instagram.model.Post;
import com.wbd.Instagram.model.User;
import com.wbd.Instagram.repository.PostRepository;
import com.wbd.Instagram.service.CommentService;
import com.wbd.Instagram.service.PostService;
import com.wbd.Instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class PostController {
//    @GetMapping("/")
//    public String greet(){
//        return "Hello There. Welcome to dummy Instagram";
//    }
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestParam("caption") String caption,
                                           @RequestParam("images")List<MultipartFile> images) throws IOException {
        Post post= postService.createPost(caption, images);
        return ResponseEntity.ok(post);
    }
    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable){
        return postRepository.findAllByCommentsOrderByCountDesc(pageable);
    }
    @GetMapping("/user/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName){
        return new ResponseEntity<>(userService.getUserByName(userName), HttpStatus.OK);
    }
    @PostMapping("/user")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }
    @PostMapping("/comments/{postId}")
    public ResponseEntity<Comment> addComment(@PathVariable long postId, @RequestBody String content,
                                              @RequestParam long userId){
        return new ResponseEntity<>(commentService.addComment(postId, content, userId), HttpStatus.OK);
    }
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id, Principal principal){
        String userName= principal.getName();
        User authenticatedUser= userService.getUserByName(userName);
        commentService.deleteComment(id, authenticatedUser);
        return ResponseEntity.noContent().build();
    }
}
