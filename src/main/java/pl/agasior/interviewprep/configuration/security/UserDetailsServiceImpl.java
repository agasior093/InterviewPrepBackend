package pl.agasior.interviewprep.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.entities.User;
import pl.agasior.interviewprep.repositories.UserRepository;

@Service
class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(this::getUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("ApplicationUser not found"));
    }

    private UserDetails getUserDetails(User user) {
        org.springframework.security.core.userdetails.User.UserBuilder builder
                = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getPassword());
        builder.roles(user.getRoles().stream().map(Role::toString).toArray(String[]::new));
        builder.disabled(!user.getIsActive());
        builder.build();
        return builder.build();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
