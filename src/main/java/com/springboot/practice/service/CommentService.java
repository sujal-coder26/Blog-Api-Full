package com.springboot.practice.service;

import com.springboot.practice.entities.Comments;
import com.springboot.practice.payloads.CommentDTO;

import java.util.List;


public interface CommentService {
    CommentDTO createComment( CommentDTO commentDTO, Integer postId);
    void deleteComment(Integer commentId);
}
