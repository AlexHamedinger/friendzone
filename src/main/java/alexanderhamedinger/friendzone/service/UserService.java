package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Friend;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.FriendRepository;
import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Qualifier("labresources")
public class UserService implements UserServiceIF, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
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
    public User getUser(String identificationCode, String identificationString) {
        //sowohl E-Mail als auch Username sind unique
        User user = new User();
        if(identificationCode == "username") {
            user = userRepository.findByUsername(identificationString);
        } else if(identificationCode == "email") {
            user = userRepository.findByEmail(identificationString);
        }
        return user;
    }
    @Override
    public User getUser(long id){
        User user;
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = null;
        }
        return user;
    }
    @Override
    public List<User> getUsers() {
        Iterable<User> userIterable = userRepository.findAll();
        Iterator<User> userIterator = userIterable.iterator();
        List<User> userCollection = new ArrayList<User>();

        while(userIterator.hasNext()) {
            userCollection.add(userIterator.next());
        }

        return userCollection;


    }
    @Override
    public List<User> getUsersLike(String username) {
        List<User> answer = userRepository.findByUsernameContaining(username);
        return answer;
    }
    @Override
    public Collection<User> getMutualFriends(long userid) {
        Collection<Friend> friendCollection = getMutuallFriends(userid);
        Collection<User> userCollection = new ArrayList<User>();
        for(Iterator<Friend> i = friendCollection.iterator(); i.hasNext();) {
            userCollection.add(getUser(i.next().getUser()));
        }
        return userCollection;
    }
    @Override
    @Transactional
    public User save(User user) {
        User neu = userRepository.save(user);
        return neu;
    }
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }



    @Override
    public Friend createFriend(Friend friend) {
        Friend neu = friendRepository.save(friend);
        return neu;
    }
    @Override
    public Friend getFriend(long user, long friend) {
        return friendRepository.findByUserAndFriend(user, friend);
    }
    @Override
    public Collection<Friend> getFriends(long friendID) {
        return friendRepository.findByFriend(friendID);
    }
    @Override
    public Collection<Friend> getMutuallFriends(long id) {
        Collection<Friend> user = friendRepository.findByUser(id);
        Collection<Friend> friend = friendRepository.findByFriend(id);
        Collection<Friend> response = new ArrayList<Friend>();

        //es wird nach gegenseitigen Freundschaften gesucht
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
