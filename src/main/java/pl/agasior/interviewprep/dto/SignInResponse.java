package pl.agasior.interviewprep.dto;

import lombok.Getter;

@Getter
public class SignInResponse {
    private final String tokenType = "Bearer";
    private final String accessToken;

    public SignInResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
