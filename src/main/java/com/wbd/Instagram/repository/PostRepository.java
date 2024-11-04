package com.wbd.Instagram.repository;

import com.wbd.Instagram.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
   // @Query("SELECT p FROM Post p ORDER BY SIZE(p.comments) DESC")
    @Query(" SELECT p.id AS postId, p.caption, c.id AS commentId, c.content, c.createdAt\n" +
            "FROM Post p\n" +
            "LEFT JOIN Comment c ON c.post.id = p.id\n" +
            "WHERE (SELECT COUNT(*) FROM Comment c2 WHERE c2.post.id = p.id AND c2.createdAt >= c.createdAt) <= 2\n" +
            "ORDER BY SIZE(p.comments) DESC, c.createdAt DESC\n ")
    Page<Post> findAllByCommentsOrderByCountDesc(Pageable pageable);

}
