package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService implements UserServiceIF {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registryUser(User user) {
        //prüft ob es bereits einen Eintrag mit dieser Email oder Usernamen gibt
        if(userRepository.findByUsername(user.getUsername()) == null) {
            if(userRepository.findByeMail(user.geteMail()) == null) {
                User neu = userRepository.save(user);
                return neu;
            }
        }
        return null;
    }
}
