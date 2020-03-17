package pl.agasior.interviewprep.services.user;

import pl.agasior.interviewprep.dto.UserDto;

import java.util.Optional;

public interface AuthenticationFacade {
    Optional<UserDto> getLoggedUser();
}
