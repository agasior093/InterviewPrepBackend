package pl.agasior.interviewprep.dto;

import lombok.Builder;
import lombok.Getter;
import pl.agasior.interviewprep.entities.Role;

import java.util.Set;

@Builder
@Getter
public class UserDto {
    private final String username;
    private final String email;
    private final Set<Role> roles;
    private final String imageUrl;
}
