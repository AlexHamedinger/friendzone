package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Friend;
import alexanderhamedinger.friendzone.entities.Likes;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface FriendRepository extends CrudRepository<Friend, Long> {
    public Friend findByUserAndFriend(long user, long friend);
    public Collection<Friend> findByFriend(long friend);
    public Collection<Friend> findByUser(long user);
}
