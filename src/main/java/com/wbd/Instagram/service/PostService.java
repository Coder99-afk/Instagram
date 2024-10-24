package com.wbd.Instagram.service;

import com.wbd.Instagram.model.Comment;
import com.wbd.Instagram.model.Image;
import com.wbd.Instagram.model.Post;
import com.wbd.Instagram.repository.CommentRepository;
import com.wbd.Instagram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CommentRepository commentRepository;

    public Post createPost(String caption, List<MultipartFile> images) throws IOException {
        Post post= new Post();
        post.setCaption(caption);
        Post savedPost= postRepository.save(post);

        List<Image> imageEntities= new ArrayList<>();
        for(MultipartFile imageFile: images){
            String imageUrl= imageService.saveImageFile(imageFile);
            Image imageEntity= new Image();
            imageEntity.setUrl(imageUrl);
            imageEntity.setPost(savedPost);
            imageEntities.add(imageEntity);
        }
        savedPost.setImages(imageEntities);
        return postRepository.save(savedPost);
    }

    public Page<Post> getAllPosts(Pageable pageable){
        //Retrieve posts sorted by the number of comments, with pagination
        return postRepository.findAllByCommentsOrderByCountDesc(pageable);
    }
    public List<Post> getPostsWithComments(){
        List<Post> posts= postRepository.findAll(Sort.by(Sort.Direction.DESC, "comments.size"));
        List<Post> updatedPosts= new ArrayList<>();
        for(Post post: posts){
            List<Comment> commentList= commentRepository.findTop2ByPostByCreated(post);
            List<Comment> last2Comments= new ArrayList<>(2);
            last2Comments.add(commentList.getLast());
            last2Comments.add(commentList.get(commentList.size()-2));
            post.setComments(last2Comments);
            updatedPosts.add(post);
        }
        return updatedPosts;
    }
}
