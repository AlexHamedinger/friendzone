package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.PostRepository;
import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PostService implements PostServiceIF{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createPost(Post post) {
        Post neu = postRepository.save(post);
        return neu;
    }
    @Override
    public Collection<Post> getPostsByPoster(String username){
        User user = userRepository.findByUsername(username);
        return postRepository.findByPoster(user.getUserID());
    }
}
