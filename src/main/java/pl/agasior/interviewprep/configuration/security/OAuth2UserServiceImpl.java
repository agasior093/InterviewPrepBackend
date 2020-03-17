package pl.agasior.interviewprep.configuration.security;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.configuration.security.oauth2.OAuth2UserInfoFactory;
import pl.agasior.interviewprep.repositories.UserRepository;
import pl.agasior.interviewprep.services.user.UserCreator;

@Service
class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final UserCreator userCreator;

    public OAuth2UserServiceImpl(UserRepository userRepository, UserCreator userCreator) {
        this.userRepository = userRepository;
        this.userCreator = userCreator;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        var oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        var user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(() -> userCreator.createUser(oAuth2UserInfo));
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
}
