package com.example.springblogrestapi.service.impl;

import com.example.springblogrestapi.dtos.PostDTO;
import com.example.springblogrestapi.dtos.PostResponse;
import com.example.springblogrestapi.entity.Post;
import com.example.springblogrestapi.exception.ResourceNotFoundException;
import com.example.springblogrestapi.repository.PostRepository;
import com.example.springblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = postDtoToPost(postDTO);
        postRepository.save(post);
        return postToPostDTO(post);
    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> postList = posts.getContent();

        List<PostDTO> postDTOList = postList.stream()
                .map(this::postToPostDTO).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setContent(postDTOList);
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return postToPostDTO(post);
    }

    @Override
    public PostDTO updatePostById(Long id, PostDTO postDTO) {
        //get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Update ", "id", id));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        postRepository.save(post);
        return postToPostDTO(post);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delete", "id", id));
        postRepository.delete(post);
    }

    private PostDTO postToPostDTO(Post post) {
        //       PostDTO postDTO = new PostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setContent(post.getContent());
//        postDTO.setDescription(post.getDescription());
        return modelMapper.map(post, PostDTO.class);

    }

    private Post postDtoToPost(PostDTO postDTO) {
//        Post post = new Post();
//        post.setId(postDTO.getId());
//        post.setTitle(postDTO.getTitle());
//        post.setContent(postDTO.getContent());
//        post.setDescription(postDTO.getDescription());
        return modelMapper.map(postDTO,Post.class);
    }


}
