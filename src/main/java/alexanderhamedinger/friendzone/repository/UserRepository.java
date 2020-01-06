package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Collection;
import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    Collection<User> findByUsernameAndEmail(String username, String email);
    List<User> findByUsernameContaining(String username);
}
