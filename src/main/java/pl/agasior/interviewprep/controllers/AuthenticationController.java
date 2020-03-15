package pl.agasior.interviewprep.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agasior.interviewprep.dto.MessageDto;
import pl.agasior.interviewprep.dto.SignInRequest;
import pl.agasior.interviewprep.dto.SignInResponse;
import pl.agasior.interviewprep.dto.SignUpRequest;
import pl.agasior.interviewprep.services.auth.SignInService;
import pl.agasior.interviewprep.services.auth.SignUpService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
class AuthenticationController {
    private final SignInService signInService;
    private final SignUpService signUpService;

    AuthenticationController(final SignInService signInService, final SignUpService signUpService) {
        this.signInService = signInService;
        this.signUpService = signUpService;
    }

    @PostMapping("/signIn")
    ResponseEntity<SignInResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(signInService.authenticate(request));
    }

    @PostMapping("/signUp")
    ResponseEntity<MessageDto> signUp(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(signUpService.signUp(request));
    }
}
