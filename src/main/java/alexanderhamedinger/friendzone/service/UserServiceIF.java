package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.User;

import java.util.Optional;

public interface UserServiceIF {

    public User createUser(User user);
    public User save(User user);
    public void setLatestRegistrationDate(String username);
    public long findIdByUsername(String username);
    public User findbyUsername(String username);
    public Optional<User> getUserById(long id);
    public void deleteUser(long id);
    public Iterable<User> getAll();

}
