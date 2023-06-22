package com.springboot.practice.controller;
import com.springboot.practice.config.Constants;
import com.springboot.practice.payloads.ApiResponse;
import com.springboot.practice.payloads.PostDTO;
import com.springboot.practice.payloads.PostResponse;
import com.springboot.practice.service.FileService;
import com.springboot.practice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;
  private final FileService fileService;

  @Value("${project.image}")
  private String path;

  @PostMapping("/user/{userId}/posts")
  public ResponseEntity<PostDTO> createPost(
      @Valid @RequestBody PostDTO postDTO,
      @PathVariable Integer userId) {
    PostDTO createPost = this.postService.createPost(postDTO, userId);
    return new ResponseEntity<>(createPost, HttpStatus.CREATED);
  }

  @PutMapping(value = "/posts/{postId}")
  public ResponseEntity<PostDTO> updatePost(
      @RequestBody PostDTO postDTO, @PathVariable Integer postId) {
    PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
    return new ResponseEntity<>(updatedPost, HttpStatus.OK);
  }
  @GetMapping(value = "/user/{userId}/posts")
  public ResponseEntity<List<PostDTO>> listOfPostByUser(@PathVariable Integer userId) {
    List<PostDTO> getAllPosts = this.postService.getPostsByUser(userId);
    return new ResponseEntity<>(getAllPosts, HttpStatus.OK );
  }

  @GetMapping(value = "/posts/{postId}")
  public ResponseEntity<PostDTO> getPostWithId(
          @PathVariable Integer postId) {
    PostDTO postWithId = this.postService.getPostById(postId);
    return ResponseEntity.ok(postWithId);
  }

  @DeleteMapping(value = "/posts/{postId}")
  public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
    this.postService.delete(postId);
    return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
  }

  @GetMapping(value = "/posts")
  public ResponseEntity<PostResponse> getAllPosts(
      @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = Constants.SORT_BY, required = false) String sortBy,
    @RequestParam(value = "sortDirection", defaultValue = Constants.SORT_DIR, required = false) String sortDirection){
    PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
    return new ResponseEntity<>(postResponse, HttpStatus.OK);

  }

  @GetMapping(value = "/posts/search/{keyword}")
  public ResponseEntity<List<PostDTO>> searchPostByTitle(
          @PathVariable String keyword) {
    List<PostDTO> searchedPost = this.postService.searchPosts ( keyword );
    return new ResponseEntity<>(searchedPost, HttpStatus.OK);
  }


  @PostMapping("/post/image/upload/{postId}")
  public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable Integer postId) throws IOException {
    PostDTO postDTO = this.postService.getPostById(postId);

   String fileName = this.fileService.uploadImage(path, image);
    postDTO.setImageName(fileName);
    PostDTO postDTO1 = this.postService.updatePost(postDTO,postId);
    return new ResponseEntity<PostDTO>(postDTO1, HttpStatus.OK);
}

@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
  public void downloadPostImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
  InputStream resource = this.fileService.getResource(path, imageName);
  response.setContentType(MediaType.IMAGE_JPEG_VALUE);
  StreamUtils.copy(resource, response.getOutputStream());
}
}
