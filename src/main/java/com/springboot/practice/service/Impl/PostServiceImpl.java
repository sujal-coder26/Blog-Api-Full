package com.springboot.practice.service.Impl;

import com.springboot.practice.entities.Post;
import com.springboot.practice.entities.User;
import com.springboot.practice.exception.ResourceNotFoundException;
import com.springboot.practice.payloads.PostDTO;
import com.springboot.practice.payloads.PostResponse;
import com.springboot.practice.repository.PostRepo;
import com.springboot.practice.repository.UserRepo;
import com.springboot.practice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepo postRepo;
  private final ModelMapper modelMapper;
  private final UserRepo userRepo;

  @Override
  public PostDTO createPost(PostDTO postDTO, Integer userId) {
    User user =
        this.userRepo
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

    Post post = this.modelMapper.map(postDTO, Post.class);
    post.setImageName("default.png");
    post.setUser(user);
    Post savedPost = this.postRepo.save(post);
    return this.modelMapper.map(savedPost, PostDTO.class);
  }

  @Override
  public PostDTO updatePost(PostDTO postDTO, Integer postId) {
    Post post =
        this.postRepo
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
    post.setContent(postDTO.getContent());
    post.setImageName(postDTO.getImageName());
    post.setTitle(postDTO.getTitle());
    Post updatedPost = this.postRepo.save(post);
    return this.modelMapper.map(updatedPost, PostDTO.class);
  }

  @Override
  public void delete(Integer postId) {
    Post post =
        this.postRepo
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
    this.postRepo.delete(post);
  }

  @Override
  public PostDTO getPostById(Integer postId) {
    Post post =
        this.postRepo
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
    return this.modelMapper.map(post, PostDTO.class);
  }

  @Override
  public List<PostDTO> getPostsByUser(Integer userId) {
    List<Post> posts =
        this.postRepo.findByUser(
            this.userRepo
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId)));
    return posts.stream()
        .map((postDto) -> this.modelMapper.map(postDto, PostDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<PostDTO> searchPosts(String keyword) {
    List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
    return posts.stream()
        .map((post) -> this.modelMapper.map(post, PostDTO.class))
        .collect(Collectors.toList());

  }

  @Override
  public PostResponse getAllPosts(
      Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
    Sort sort =
        (sortDirection.equalsIgnoreCase("asc"))
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    Page<Post> pagePost = this.postRepo.findAll(pageable);
    List<Post> allPosts = pagePost.getContent();

    List<PostDTO> postDTOS =
        allPosts.stream()
            .map((post) -> this.modelMapper.map(post, PostDTO.class))
            .collect(Collectors.toList());

    PostResponse postResponse = new PostResponse();
    postResponse.setContent(postDTOS);
    postResponse.setPageNumber(pagePost.getNumber());
    postResponse.setPageSize(pagePost.getSize());
    postResponse.setTotalElements(pagePost.getTotalElements());
    postResponse.setTotalPages(pagePost.getTotalPages());
    postResponse.setLastPage(pagePost.isLast());

    return postResponse;
  }
}
