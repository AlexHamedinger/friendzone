package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.Post;

import java.util.Collection;
import java.util.Optional;

public interface PostServiceIF {
    public Post createPost(Post post);
    public Collection<Post> getPostsByPoster(String username);
    public Optional<Post> getPostById(long id);
    public Post save(Post post);
    public void deletePost(long id);
    public Collection<Post> getLatestPosts(int maxPosts);

    public Likes createLike(Likes like);
    public boolean isLikeUnique(Likes like);
    public void deleteLike(Likes like);
    public Likes getCompleteLike(Likes like);
}
