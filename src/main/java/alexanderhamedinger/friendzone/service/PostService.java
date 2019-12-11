package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements PostServiceIF{

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        Post neu = postRepository.save(post);
        return neu;
    }
}
