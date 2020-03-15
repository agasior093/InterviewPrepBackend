package pl.agasior.interviewprep.repositories;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    UserRepository() {
        this.users.put("1",
                User.builder()
                        .id("1")
                        .username("admin")
                        .email("admin@admin.com")
                        .password(new BCryptPasswordEncoder().encode("admin"))
                        .isActive(true)
                        .roles(Set.of(Role.Admin))
                        .build());
    }

    public Optional<User> findByUsername(String username) {
        return users.values().stream().filter(user -> username.equals(user.getUsername())).findFirst();
    }
}

