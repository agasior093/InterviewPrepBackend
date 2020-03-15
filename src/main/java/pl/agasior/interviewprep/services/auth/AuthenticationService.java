package pl.agasior.interviewprep.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.configuration.security.jwt.TokenProvider;
import pl.agasior.interviewprep.dto.LoginRequest;
import pl.agasior.interviewprep.dto.LoginResponse;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    AuthenticationService(final AuthenticationManager authenticationManager, final TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new LoginResponse(tokenProvider.createToken(authentication));
    }
}
