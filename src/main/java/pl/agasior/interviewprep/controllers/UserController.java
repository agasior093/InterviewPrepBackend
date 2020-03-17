package pl.agasior.interviewprep.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agasior.interviewprep.dto.UserDto;
import pl.agasior.interviewprep.services.user.AuthenticationFacade;

@RestController
@RequestMapping("/user")
class UserController {
    private final AuthenticationFacade authenticationFacade;

    public UserController(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping
    ResponseEntity<UserDto> getLoggedUser() {
        return authenticationFacade.getLoggedUser().map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
