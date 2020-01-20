package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Friend;
import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserServiceIF {

    //Create
    public User createUser(User user);
    //Read
    public User getUser(String identificationCode, String identificationString);
    public User getUser(long id);
    public List<User> getUsers(String partnershop);
    public List<User> getUsersLike(String username, String partnershop);
    public Collection<User> getMutualFriends(long id);
    //Update
    public User save(User user);
    //Delete
    public void deleteUser(long id);

    //Create
    public Friend createFriend(Friend friend);
    //Read
    public Friend getFriend(long user, long friend);
    public Collection<Friend> getFriends(long friendID);
    public Collection<Friend> getMutuallFriends(long id);
    //Delete
    public void deleteFriend(Friend friend);

}
