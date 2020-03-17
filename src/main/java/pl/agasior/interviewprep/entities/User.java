package pl.agasior.interviewprep.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Builder
@EqualsAndHashCode
@Getter
public class User {
    @Id
    private String id;
    private final String username;
    private final String email;
    private final String password;
    private final Set<Role> roles;
    private final Boolean isActive;
    private final AuthProvider provider;
    private final String imageUrl;
}
