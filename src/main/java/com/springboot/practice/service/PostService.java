
package com.springboot.practice.service;
import com.springboot.practice.payloads.PostDTO;
import com.springboot.practice.payloads.PostResponse;

import java.util.List;


public interface PostService {
PostDTO createPost( PostDTO postDTO, Integer userId );
PostDTO updatePost(PostDTO postDTO, Integer postId);
void delete (Integer postId);
PostDTO getPostById(Integer postId);
List<PostDTO> getPostsByUser(Integer userId);
List<PostDTO> searchPosts(String keyword);
PostResponse getAllPosts ( Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

}
