package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Likes;
import org.springframework.data.repository.CrudRepository;

public interface LikesRepository extends CrudRepository<Likes, Long> {
}
