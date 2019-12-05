package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
