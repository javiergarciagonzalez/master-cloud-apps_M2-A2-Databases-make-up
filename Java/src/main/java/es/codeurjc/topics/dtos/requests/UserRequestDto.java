package es.codeurjc.topics.dtos.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank(message = "UserName is mandatory")
    private String userName;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;

}
