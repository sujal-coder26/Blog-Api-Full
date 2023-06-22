package com.springboot.practice.repository;

import com.springboot.practice.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Integer> {
}
