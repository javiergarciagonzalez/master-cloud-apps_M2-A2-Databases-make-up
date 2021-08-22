package es.codeurjc.topics.dtos.responses;

import lombok.Data;

@Data
public class UserPostResponseDto {

    private Long id;
    private Long topicId;
    private String post;
    private String title;

}
