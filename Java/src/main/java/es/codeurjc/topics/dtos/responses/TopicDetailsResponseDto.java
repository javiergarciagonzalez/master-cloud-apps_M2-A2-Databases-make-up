package es.codeurjc.topics.dtos.responses;

import java.util.Collection;

import lombok.Data;

@Data
public class TopicDetailsResponseDto {

    private Long id;
    private String title;
    private String author;
    private String message;
    private Collection<PostResponseDto> posts;

}
