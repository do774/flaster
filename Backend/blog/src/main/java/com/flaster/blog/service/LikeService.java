package com.flaster.blog.service;

import com.flaster.blog.model.Like;
import com.flaster.blog.model.Post;
import com.flaster.blog.model.User;
import com.flaster.blog.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

	@Autowired
	private LikeRepository likeRepository;

	public Like addLike(Post post, User user) {
		if (!likeRepository.existsByPostIdAndUserId(post.getId(), user.getId())) {
			Like like = new Like();
			like.setPost(post);
			like.setUser(user);
			return likeRepository.save(like);
		}
		return null;
	}

	public long countLikes(Post post) {
		return likeRepository.findAll().stream().filter(l -> l.getPost().getId().equals(post.getId())).count();
	}

	public boolean removeLike(Post post, User user) {
		Optional<Like> optLike = likeRepository.findByPostIdAndUserId(post.getId(), user.getId());
		if (optLike.isPresent()) {
			likeRepository.delete(optLike.get());
			return true;
		}
		return false;
	}

	public boolean isLikedByUser(Post post, User user) {
		return likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());
	}
}
