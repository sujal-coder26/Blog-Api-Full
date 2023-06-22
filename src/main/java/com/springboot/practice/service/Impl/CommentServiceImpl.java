package com.springboot.practice.service.Impl;

import com.springboot.practice.entities.Comments;
import com.springboot.practice.entities.Post;
import com.springboot.practice.exception.ResourceNotFoundException;
import com.springboot.practice.payloads.CommentDTO;
import com.springboot.practice.repository.CommentRepo;
import com.springboot.practice.repository.PostRepo;
import com.springboot.practice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepo commentRepo;
  private final ModelMapper modelMapper;
  private final PostRepo postRepo;

  @Override
  public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
    Post post =
        this.postRepo
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
    Comments comment = this.modelMapper.map (commentDTO, Comments.class);
    comment.setPost ( post );
    Comments savedComments = this.commentRepo.save(comment);
    return this.modelMapper.map(savedComments, CommentDTO.class);
  }

  @Override
  public void deleteComment(Integer commentId) {
      Comments commentTODelete=
              this.commentRepo
                      .findById(commentId)
                      .orElseThrow(() -> new ResourceNotFoundException("Comments", "comment_id", commentId));
      this.commentRepo.delete ( commentTODelete );

  }

}
