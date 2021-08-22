package es.codeurjc.topics.dtos.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDto {

    @NotNull(message = "User id is mandatory")
    private Long userId;
    @NotBlank(message = "Post is mandatory")
    private String post;
    private String title;

}
