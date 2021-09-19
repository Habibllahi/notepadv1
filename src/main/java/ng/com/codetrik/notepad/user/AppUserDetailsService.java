package ng.com.codetrik.notepad.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> nullableUser = Optional.ofNullable(userRepository.findByUsername(username));
        if(nullableUser.isPresent())
            return new AppUserDetails(nullableUser.get());
        else
            throw  new UsernameNotFoundException("User is not previously created");

    }
}
