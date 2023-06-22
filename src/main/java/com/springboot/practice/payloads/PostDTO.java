package com.springboot.practice.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.xml.stream.events.Comment;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

  private int postId;

  @NotEmpty
  @Size(min = 4, message = "Title must be min of 3 characters.")
  private String title;

  @NotEmpty
  @Size(min = 4, max = 100, message = "Content must be min of 3 characters and max of 100 char")
  private String content;

  private String imageName;


  private UserDTO user;

  private Set<Comment> comments = new HashSet<>();
}
