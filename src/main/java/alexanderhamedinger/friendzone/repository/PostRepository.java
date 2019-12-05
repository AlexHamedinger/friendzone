package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
