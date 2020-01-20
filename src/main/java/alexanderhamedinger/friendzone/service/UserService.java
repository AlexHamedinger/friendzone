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
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService implements UserServiceIF, UserDetailsService {

    @Autowired
    private RestTemplate restServiceClient;
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
    public List<User> getUsers(String partnershop) {
        List<User> userCollection = new ArrayList<User>();

        //für die "normale" Suche
        if(partnershop == null) {
            Iterable<User> userIterable = userRepository.findAll();
            Iterator<User> userIterator = userIterable.iterator();
            while (userIterator.hasNext()) {
                userCollection.add(userIterator.next());
            }
        }
        //für die Suche nach Usern in einem Partnershop
        else {
            //das sind dann bald die Partnershop E-Mails
            List<String> emails = getPartnershopEmails(partnershop);
            User user = new User();

            for(Iterator<String> i = emails.iterator(); i.hasNext(); ) {
                user = userRepository.findByEmail(i.next());
                if(user != null) {
                    userCollection.add(user);
                }
            }
        }

        return userCollection;
    }
    @Override
    public List<User> getUsersLike(String username, String partnershop) {
        List<User> users = new ArrayList<>();
        users = userRepository.findByUsernameContaining(username);

        //für die Suche nach Usern in einem Partnershop
        if(partnershop != null) {
            List<String> emails = getPartnershopEmails(partnershop);
            List<User> users2 = new ArrayList<>();
            User currentUser;
            String currentEmail;

            for(Iterator<User> i = users.iterator(); i.hasNext(); ) {
                currentUser = i.next();
                for(Iterator<String> j = emails.iterator(); j.hasNext(); ) {
                    currentEmail = j.next();
                    if(currentUser.getEmail().equals(currentEmail)) {
                        users2.add(currentUser);
                        break;
                    }
                }
            }
            users = users2;
        }
        return users;
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

    //Hilfsfunktion für die Suche in Partnershops
    private List<String> getPartnershopEmails(String partnershop) {
        List<String> emails = new ArrayList<>();
//        if(partnershop.equals("dummy")) {
//            emails.add("hallo@email.com");
//            emails.add("jahni@ssv.de");
//            emails.add("popel@kaka.stink");
//            emails.add("donald.trump@president.com");
//            emails.add("luke.skywalker@imperium.de");
//        }
//        String email = restServiceClient.getForObject("", String.class);

        String url = "";
        if(partnershop.equals("bikerator")) {
            url = "";
        }
        if(partnershop.equals("comicshop")) {
            url = "";
        }
        if(partnershop.equals("dummy")) {
            emails.add("jahni@ssv.de");
            emails.add("jesus@christus.de");
            emails.add("donald.trump@president.com");
            emails.add("luke.skywalker@imperium.de");
            return emails;
        }

        try {
            emails = restServiceClient.postForObject("http://localhost:1889/restapi/customers/emails", "", ArrayList.class);

        } catch(Exception e) {

            e.printStackTrace();
        }
        return emails;
    }
}
