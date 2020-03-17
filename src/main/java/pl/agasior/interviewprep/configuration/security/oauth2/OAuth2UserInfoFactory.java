package pl.agasior.interviewprep.configuration.security.oauth2;

import pl.agasior.interviewprep.entities.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.Facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new RuntimeException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
