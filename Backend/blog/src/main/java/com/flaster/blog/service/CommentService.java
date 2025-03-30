package com.flaster.blog.service;

import com.flaster.blog.model.Comment;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;

	public List<Comment> getCommentsByPost(Post post) {
		return commentRepository.findAll().stream().filter(c -> c.getPost().getId().equals(post.getId())).toList();
	}

	public Comment createComment(Post post, User user, String content) {
		Comment comment = new Comment();
		comment.setPost(post);
		comment.setUser(user);
		comment.setContent(content);
		comment.setCreatedAt(LocalDateTime.now());
		return commentRepository.save(comment);
	}

	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}
}
