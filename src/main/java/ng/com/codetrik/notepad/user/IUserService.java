/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.user;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ng.com.codetrik.notepad.util.Authenticate;

import java.util.UUID;

public interface IUserService {
    Single<User> createUser(User user);
    Single<User> UpdateUser(User user, UUID id);
    Single<Authenticate> userExist();
}
