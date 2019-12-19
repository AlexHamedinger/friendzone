package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.LikeRepository;
import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.Optional;

@Service
@Qualifier("labresources")
public class UserService implements UserServiceIF, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.findByUsername(user.getUsername()) != null) {
            return null;
        } else {
            User neu = userRepository.save(user);
            return neu;
        }
    }
    @Override
    public User save(User user) {
        User neu = userRepository.save(user);
        return neu;
    }
    @Override
    public void setLatestRegistrationDate(String username) {
        User user = userRepository.findByUsername(username);
        user.setLatestRegistration(new GregorianCalendar());
        userRepository.save(user);
    }
    @Override
    public long findIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getId();
    }
    @Override
    public User findbyUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
    @Override
    public Optional<User> getUserById(long id){
        Optional<User> user = userRepository.findById(id);
        return user;
    }
    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }


    //#### WIRD VON USERDETAILSSERVICE BENÖTIGT #####
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
