package com.wbd.Instagram.repository;

import com.wbd.Instagram.model.Comment;
import com.wbd.Instagram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post.id = :post.getId() ")
    List<Comment> findTop2ByPostByCreated(Post post);
}
