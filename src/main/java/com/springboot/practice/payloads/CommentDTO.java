package com.springboot.practice.payloads;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDTO {
    private int id;
    private int content;
}
