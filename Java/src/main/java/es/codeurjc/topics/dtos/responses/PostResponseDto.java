package es.codeurjc.topics.dtos.responses;

import lombok.Data;

@Data
public class PostResponseDto {

    private Long id;
    private PostUserResponseDto user;
    private String title;
    private String post;

}
