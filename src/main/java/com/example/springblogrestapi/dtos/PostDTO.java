package com.example.springblogrestapi.dtos;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;

    //title should have at least two characters
    //title should not be empty

    @NotEmpty
    @Size(min = 2, message = "post title should have at least 2 characters")
    private String title;


    @NotEmpty
    @Size(min = 10 , message = "description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
}
