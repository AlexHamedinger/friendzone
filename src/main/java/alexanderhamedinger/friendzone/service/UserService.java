package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Friend;
import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.FriendRepository;
import alexanderhamedinger.friendzone.repository.LikeRepository;
import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
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
    @Autowired
    private FriendRepository friendRepository;


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
    public Collection<User> getAll() {
        Iterable<User> userIterable = userRepository.findAll();
        Iterator<User> userIterator = userIterable.iterator();
        //TODO: User-Collection "ordentlich" initialisieren
        Collection<User> userCollection = userRepository.findByUsernameAndEmail("User", "hallo@email.de");
        userCollection.clear();
        
        while(userIterator.hasNext()) {
            userCollection.add(userIterator.next());
        }

        return userCollection;
    }

    
    

    @Override
    public Friend createFriend(Friend friend) {
        Friend neu = friendRepository.save(friend);
        return neu;
    }
    @Override
    public Friend findFriendByIds(long user, long friend) {
        return friendRepository.findByUserAndFriend(user, friend);
    }
    @Override
    public void deleteFriend(Friend friend) {
        friendRepository.delete(friend);
    }


    //#### WIRD VON USERDETAILSSERVICE BENÖTIGT #####
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
