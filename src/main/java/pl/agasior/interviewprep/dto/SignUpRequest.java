package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class SignUpRequest {
    @NotBlank(message = "Username is required")
    private final String username;

    @NotBlank(message = "Password is required")
    private final String password;

    @NotBlank(message = "Password confirmation is required")
    private final String passwordConfirmation;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private final String email;
}
