package alexanderhamedinger.friendzone.repository;

import alexanderhamedinger.friendzone.entities.Likes;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Likes, Long> {
    public Likes findByLikerAndPost(long liker, long post);
}
