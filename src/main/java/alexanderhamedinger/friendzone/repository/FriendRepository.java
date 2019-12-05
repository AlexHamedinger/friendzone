package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendRepository extends CrudRepository<Friend, Long> {
}
