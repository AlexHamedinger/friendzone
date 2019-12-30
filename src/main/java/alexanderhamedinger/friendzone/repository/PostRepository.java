package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PostRepository extends CrudRepository<Post, Long> {
    Collection<Post> findByPoster(long poster);  //automatisch OrderByIdDesc
    Collection<Post> findByPosterOrderByIdDesc(long poster);
    Collection<Post> findAllByOrderByIdDesc();
}
