package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.User;

public interface UserServiceIF {

    public User createUser(User user);
    public void setLatestRegistrationDate(String username);
    public long findIdByUsername(String username);
}
