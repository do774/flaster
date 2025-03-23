package com.flaster.blog.repository;

import com.flaster.blog.model.Comment;
import com.flaster.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
