package com.example.springblogrestapi.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10 , message = "Comment body at least should be 5 character")
    private String body;
}
