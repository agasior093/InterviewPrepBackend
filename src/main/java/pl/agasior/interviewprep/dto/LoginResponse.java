package pl.agasior.interviewprep.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final String tokenType = "Bearer";
    private final String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
