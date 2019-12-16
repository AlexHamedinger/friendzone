package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostServiceIF {
    public Post createPost(Post post);
    public Collection<Post> getPostsByPoster(String username);
    public Optional<Post> getPostById(long id);
}
