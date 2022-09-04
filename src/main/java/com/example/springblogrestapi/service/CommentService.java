package com.example.springblogrestapi.service;

import com.example.springblogrestapi.dtos.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId,CommentDTO commentDTO);
    List<CommentDTO> getCommentByPostId(long postId);

    CommentDTO getCommentById(Long postID,Long commentId);

    CommentDTO updateComment(Long postIdD,Long commentId,CommentDTO commentDTO);

    void deleteCommentById(Long postId,long commentId);
}
