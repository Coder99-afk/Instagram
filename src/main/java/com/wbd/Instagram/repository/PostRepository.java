package com.wbd.Instagram.repository;

import com.wbd.Instagram.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY SIZE(p.comments) DESC")
    Page<Post> findAllByCommentsOrderByCountDesc(Pageable pageable);

}
