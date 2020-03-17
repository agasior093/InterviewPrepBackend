package pl.agasior.interviewprep.configuration.security.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import pl.agasior.interviewprep.configuration.security.jwt.TokenProvider;
import pl.agasior.interviewprep.repositories.CookieRequestRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final CookieRequestRepository cookieRequestRepository;

    public OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, CookieRequestRepository cookieRequestRepository) {
        this.tokenProvider = tokenProvider;
        this.cookieRequestRepository = cookieRequestRepository;
    }

    @Value("${security.oauth2.authorizedRedirectUrl}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = tokenProvider.createToken(authentication);
        return UriComponentsBuilder.fromUriString(redirectUrl + "/" + token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        cookieRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
