/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.user;

import io.reactivex.rxjava3.core.Single;
import ng.com.codetrik.notepad.exceptions.UserAlreadyExistException;
import ng.com.codetrik.notepad.util.Authenticate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService{
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IUserRepository userRepository;
    @Override
    public Single<User> createUser(User user) {
        return Single.just(createUserProcess(user));
    }

    @Override
    public Single<User> UpdateUser(User user, UUID id) {
        return Single.just(updateUserProcess(user, id));
    }

    @Override
    public Single<Authenticate> userExist() {
        return Single.just(userExistProcess());
    }

    private User createUserProcess(User user){
        if(user.id != null){
            var optionalUser = userRepository.findById(user.getId());
            if(optionalUser.isPresent()) throw new UserAlreadyExistException("user with username " + user.getUsername() + " already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialNonExpired(true);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    private User updateUserProcess(User user, UUID id) {
        return userRepository.findById(id).map(fetchedUser->{
            fetchedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            fetchedUser.setUsername(user.getUsername());
            fetchedUser.setFirst_name(user.getFirst_name());
            fetchedUser.setLast_name(user.getLast_name());
            if(user.isAdmin()){
                fetchedUser.setAccountNonExpired(user.isAccountNonExpired());
                fetchedUser.setCredentialNonExpired(user.isCredentialNonExpired());
                fetchedUser.setAccountNonLocked(user.isAccountNonLocked());
                fetchedUser.setEnabled(user.isEnabled());
            }
            return userRepository.save(fetchedUser);
        }).get();
    }

    private Authenticate userExistProcess(){
        return new Authenticate(true);
    }
}
