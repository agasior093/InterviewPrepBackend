package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class SignInRequest {
    @NotBlank(message = "Username is required")
    private final String username;
    @NotBlank(message = "Password is required")
    private final String password;
}
