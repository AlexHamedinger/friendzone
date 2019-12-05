package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService implements UserServiceIF {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        User neu = userRepository.save(user);
        return neu;

    }
}
