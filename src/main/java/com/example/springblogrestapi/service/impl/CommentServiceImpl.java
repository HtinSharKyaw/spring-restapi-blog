package com.example.springblogrestapi.service.impl;

import com.example.springblogrestapi.dtos.CommentDTO;
import com.example.springblogrestapi.entity.Comment;
import com.example.springblogrestapi.entity.Post;
import com.example.springblogrestapi.exception.ResourceNotFoundException;
import com.example.springblogrestapi.repository.CommentRepository;
import com.example.springblogrestapi.repository.PostRepository;
import com.example.springblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        //return commentRepository.save(commentDTO );
        Comment comment = mapToEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Argument", "id", postId));
        comment.setPost(post);
        //save comment to the database;
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postID, Long commentId) {
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("post", "id", postID));
        Comment resultComment = commentRepository.findByPostId(post.getId())
                .stream()
                .filter(comment -> comment.getId() == commentId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        return mapToDTO(resultComment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findByPostId(post.getId())
                .stream()
                .filter(comment1 -> comment1.getId() == commentId)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        commentRepository.save(comment);
        return mapToDTO(comment);
    }

    @Override
    public void deleteCommentById(Long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new ResourceNotFoundException("comment", "id", commentId);
        }
        commentRepository.delete(comment);
    }

    private CommentDTO mapToDTO(Comment comment) {
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setBody(comment.getBody());
//        commentDTO.setEmail(comment.getEmail());
        return modelMapper.map(comment,CommentDTO.class);
    }

    private Comment mapToEntity(CommentDTO commentDTO) {
//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        return modelMapper.map(commentDTO,Comment.class);
    }
}
