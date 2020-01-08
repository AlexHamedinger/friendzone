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
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

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
        User neu = new User();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(userRepository.findByUsername(user.getUsername()) != null) {
            neu.setUsername("Username");
        } else if(userRepository.findByEmail(user.getEmail()) != null) {
            neu.setUsername("EMail");
        } else {
            neu = userRepository.save(user);
        }
        return neu;
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
    public List<User> getAll() {
        Iterable<User> userIterable = userRepository.findAll();
        Iterator<User> userIterator = userIterable.iterator();
        List<User> userCollection = new ArrayList<User>();
        
        while(userIterator.hasNext()) {
            userCollection.add(userIterator.next());
        }

        return userCollection;
    }
    @Override
    public Collection<User> getRealUserFriends(long userid) {
        Collection<Friend> friendCollection = getRealFriends(userid);
        Collection<User> userCollection = new ArrayList<User>();
        for(Iterator<Friend> i = friendCollection.iterator(); i.hasNext();) {
            userCollection.add(getUserById(i.next().getUser()).get());
        }
        return userCollection;
    }
    @Override
    public List<User> findUserLikeUsername(String username) {
        List<User> answer = userRepository.findByUsernameContaining(username);
        return answer;
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
    @Override
    public Collection<Friend> getFriendByFriend(long id) {
        return friendRepository.findByFriend(id);
    }
    @Override
    public Collection<Friend> getRealFriends(long id) {
        Collection<Friend> user = friendRepository.findByUser(id);
        Collection<Friend> friend = friendRepository.findByFriend(id);
        Collection<Friend> response = new ArrayList<Friend>();

        for(Iterator<Friend> userIterator = user.iterator(); userIterator.hasNext(); ) {
            Friend thisUser = userIterator.next();
            for(Iterator<Friend> friendIterator = friend.iterator(); friendIterator.hasNext(); ) {
                Friend thisFriend = friendIterator.next();
                if(thisUser.getFriend() == thisFriend.getUser()) {
                    response.add(thisFriend);
                }
            }
        }

        return response;
    }



    //#### WIRD VON USERDETAILSSERVICE BENÖTIGT #####
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
