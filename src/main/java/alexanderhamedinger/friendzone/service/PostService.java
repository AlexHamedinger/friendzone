package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.LikeRepository;
import alexanderhamedinger.friendzone.repository.PostRepository;
import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Optional;

@Service
public class PostService implements PostServiceIF{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;


    @Override
    public Post createPost(Post post) {
        Post neu = postRepository.save(post);
        return neu;
    }
    @Override
    public Collection<Post> getPostsByPoster(String username){
        User user = userRepository.findByUsername(username);
        return postRepository.findByPosterOrderByIdDesc(user.getId());
    }
    @Override
    public Optional<Post> getPostById(long id) {
        return postRepository.findById(id);
    }
    @Override
    public Post save(Post post) {
        Post neu = postRepository.save(post);
        return neu;
    }
    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
    @Override
    public Collection<Post> getLatestPosts(int maxPosts, long userid) {
        Collection<Post> posts = postRepository.findByPoster(1L);
        posts.clear();
        Iterable<Post> postIterable = postRepository.findAllByOrderByIdDesc();
        Iterator<Post> postIterator = postIterable.iterator();
        int count = 0;
        Post post;

        while(count < maxPosts && postIterator.hasNext()) {
            post = postIterator.next();
            if(post.getUser().getId() != userid) {
                posts.add(post);
                count++;
            }
        }

        return posts;
    }



    @Override
    public Likes createLike(Likes like) {
        like.setCreationDate(new GregorianCalendar());
        Likes neu = likeRepository.save(like);
        return neu;
    }
    @Override
    public boolean isLikeUnique(Likes like) {
        Likes other = likeRepository.findByLikerAndPost(like.getLiker(), like.getPost());
        if(other == null)
            return true;
        return false;
    }
    @Override
    public void deleteLike(Likes like) {
        long id;
        if(like.hasId()) {
            id = like.getId();
        } else {
            Likes other = likeRepository.findByLikerAndPost(like.getLiker(), like.getPost());
            id = other.getId();
        }
        likeRepository.deleteById(id);
    }
    @Override
    public Likes getCompleteLike(Likes like) {
        like = likeRepository.findByLikerAndPost(like.getLiker(), like.getPost());
        return like;
    }
}
