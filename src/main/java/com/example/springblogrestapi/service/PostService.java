package com.example.springblogrestapi.service;

import com.example.springblogrestapi.dtos.PostDTO;
import com.example.springblogrestapi.dtos.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDTO getPostById(Long id);

    PostDTO updatePostById(Long id, PostDTO postDTO);

    void deletePostById(Long id);
}
