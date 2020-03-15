package pl.agasior.interviewprep.services.auth;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.MessageDto;
import pl.agasior.interviewprep.dto.SignUpRequest;
import pl.agasior.interviewprep.services.user.UserCreator;

@Service
public class SignUpService {
    private final UserCreator userCreator;

    SignUpService(final UserCreator userCreator) {
        this.userCreator = userCreator;
    }

    public MessageDto signUp(SignUpRequest request) {
        final var user = userCreator.createUser(request);
        return new MessageDto("User " + user.getUsername() + " successfully created");
    }
}
