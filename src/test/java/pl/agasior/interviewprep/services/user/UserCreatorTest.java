package pl.agasior.interviewprep.services.user;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.agasior.interviewprep.dto.SignUpRequest;
import pl.agasior.interviewprep.dto.exceptions.EmailAlreadyExistsException;
import pl.agasior.interviewprep.dto.exceptions.PasswordDoesNotMatchException;
import pl.agasior.interviewprep.dto.exceptions.UserNameAlreadyExistsException;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.entities.User;
import pl.agasior.interviewprep.repositories.UserRepository;
import pl.agasior.interviewprep.testutils.DatabasePreparer;
import pl.agasior.interviewprep.testutils.RequestFactory;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserCreatorTest {

    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private DatabasePreparer databasePreparer;

    @Autowired
    private UserRepository userRepository;

    private final MockMvc mockMvc;

    UserCreatorTest(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    @AfterAll
    void clearDatabase() {
        databasePreparer.clear();
    }

    @Test
    void createNewUser() throws Exception {
        final var request = SignUpRequest.builder()
                .username("testUser")
                .email("test@test.pl")
                .password("pass")
                .passwordConfirmation("pass")
                .build();

        mockMvc.perform(requestFactory.signUp(request))
                .andDo(print())
                .andExpect(status().isOk());

        userRepository.findByUsername(request.getUsername()).ifPresentOrElse(user -> {
            Assertions.assertEquals(user.getUsername(), request.getUsername());
            Assertions.assertEquals(user.getEmail(), request.getEmail());
            Assertions.assertEquals(user.getRoles(), Set.of(Role.User));
            Assertions.assertTrue(user.getIsActive());
        }, () -> Assertions.fail("User not found"));
    }


    @Nested
    class ThrowValidationError {

        @Test
        void usernameAlreadyExists() throws Exception {
            final var username = "testUser";
            userRepository.save(User.builder().username(username).build());
            final var request = SignUpRequest.builder()
                    .username(username)
                    .email("test@test.pl")
                    .password("pass")
                    .passwordConfirmation("pass")
                    .build();

            final var mvcResult = mockMvc.perform(requestFactory.signUp(request))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andReturn();

            Assertions.assertEquals(new UserNameAlreadyExistsException().getMessage(), mvcResult.getResponse().getContentAsString());
        }

        @Test
        void emailAlreadyExists() throws Exception {
            final var email = "test@mail.com";
            userRepository.save(User.builder().email(email).build());
            final var request = SignUpRequest.builder()
                    .username("testUser")
                    .email(email)
                    .password("pass")
                    .passwordConfirmation("pass")
                    .build();

            final var mvcResult = mockMvc.perform(requestFactory.signUp(request))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andReturn();

            Assertions.assertEquals(new EmailAlreadyExistsException().getMessage(), mvcResult.getResponse().getContentAsString());
        }

        @Test
        void passwordsDoesNotMatch() throws Exception {
            final var password = "password";
            final var request = SignUpRequest.builder()
                    .username("testUser")
                    .email("test@mail.com")
                    .password(password)
                    .passwordConfirmation(password.toUpperCase())
                    .build();

            final var mvcResult = mockMvc.perform(requestFactory.signUp(request))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andReturn();

            Assertions.assertEquals(new PasswordDoesNotMatchException().getMessage(), mvcResult.getResponse().getContentAsString());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void nullOrEmptyUsername(String username) throws Exception {
            final var request = SignUpRequest.builder()
                    .username(username)
                    .email("test@test.pl")
                    .password("pass")
                    .passwordConfirmation("pass")
                    .build();

            mockMvc.perform(requestFactory.signUp(request))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void nullOrEmptyEmail(String email) throws Exception {
            final var request = SignUpRequest.builder()
                    .username("testUser")
                    .email(email)
                    .password("pass")
                    .passwordConfirmation("pass")
                    .build();

            mockMvc.perform(requestFactory.signUp(request))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void nullOrEmptyPassword(String password) throws Exception {
            final var request = SignUpRequest.builder()
                    .username("testUser")
                    .email("test@mail.pl")
                    .password(password)
                    .passwordConfirmation("pass")
                    .build();

            mockMvc.perform(requestFactory.signUp(request))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        void nullOrEmptyPasswordConfirmation(String password) throws Exception {
            final var request = SignUpRequest.builder()
                    .username("testUser")
                    .email("test@mail.pl")
                    .password("pass")
                    .passwordConfirmation(password)
                    .build();

            mockMvc.perform(requestFactory.signUp(request))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @ValueSource(strings = {"mailmail.com", "@mail.com", "@mail@mail.com"})
        void invalidEmail(String email) throws Exception {
            final var request = SignUpRequest.builder()
                    .username("testUser")
                    .email(email)
                    .password("pass")
                    .passwordConfirmation("pass")
                    .build();

            mockMvc.perform(requestFactory.signUp(request))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}
