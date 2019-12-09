package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.UserRepository;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Qualifier("labresources")
public class UserService implements UserServiceIF, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User neu = userRepository.save(user);
        return neu;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        return user;
    }
}
