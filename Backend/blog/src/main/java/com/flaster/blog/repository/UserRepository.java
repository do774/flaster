package com.flaster.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flaster.blog.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}