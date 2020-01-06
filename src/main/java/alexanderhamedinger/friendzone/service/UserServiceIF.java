package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Friend;
import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserServiceIF {

    public User createUser(User user);
    public User save(User user);
    public void setLatestRegistrationDate(String username);
    public long findIdByUsername(String username);
    public User findbyUsername(String username);
    public Optional<User> getUserById(long id);
    public void deleteUser(long id);
    public List<User> getAll();
    public Collection<User> getRealUserFriends(long id);
    public List<User> findUserLikeUsername(String username);

    public Friend createFriend(Friend friend);
    public Friend findFriendByIds(long user, long friend);
    public void deleteFriend(Friend friend);
    public Collection<Friend> getFriendByFriend(long id);
    public Collection<Friend> getRealFriends(long id);

}
