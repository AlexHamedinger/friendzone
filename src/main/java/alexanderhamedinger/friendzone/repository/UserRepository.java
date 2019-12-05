package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
