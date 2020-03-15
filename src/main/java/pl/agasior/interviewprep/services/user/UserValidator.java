package pl.agasior.interviewprep.services.user;

import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.SignUpRequest;
import pl.agasior.interviewprep.dto.exceptions.EmailAlreadyExistsException;
import pl.agasior.interviewprep.dto.exceptions.PasswordDoesNotMatchException;
import pl.agasior.interviewprep.dto.exceptions.UserNameAlreadyExistsException;
import pl.agasior.interviewprep.repositories.UserRepository;

@Service
class UserValidator {
    private final UserRepository userRepository;

    UserValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO Vavr::Validation would be great here
    void validate(SignUpRequest request) throws RuntimeException {
        if (!request.getPassword().equals(request.getPasswordConfirmation()))
            throw new PasswordDoesNotMatchException();
        if (userRepository.findByUsername(request.getUsername()).isPresent())
            throw new UserNameAlreadyExistsException();
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            throw new EmailAlreadyExistsException();
    }
}
