package pl.agasior.interviewprep.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.configuration.security.jwt.TokenProvider;
import pl.agasior.interviewprep.dto.SignInRequest;
import pl.agasior.interviewprep.dto.SignInResponse;

@Service
public class SignInService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    SignInService(final AuthenticationManager authenticationManager, final TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public SignInResponse authenticate(SignInRequest request) {
        final var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new SignInResponse(tokenProvider.createToken(authentication));
    }
}
