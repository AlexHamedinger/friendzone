package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Likes;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface LikeRepository extends CrudRepository<Likes, Long> {
    Likes findByLikerAndPost(long liker, long post);
    Collection<Likes> findByPost(long post);
}
