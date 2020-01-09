package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByCommentedPostOrderByIdDesc(long commentedPost);
    Iterable<Comment> findByCommentedPost(long commentedPost);
}
