package pl.agasior.interviewprep.testutils;

import pl.agasior.interviewprep.dto.UserDto;
import pl.agasior.interviewprep.entities.Role;

import java.util.Set;

public class UserFactory {

    public static UserDto user(String username) {
        return UserDto.builder().username(username).roles(Set.of(Role.User)).build();
    }
}
