package es.codeurjc.topics.dtos.responses;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String userName;
    private String email;

}
