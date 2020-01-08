package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.repository.LikeRepository;
import alexanderhamedinger.friendzone.repository.PostRepository;
import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Collection<Post> getPosts(String username){
        User user = userRepository.findByUsername(username);
        return postRepository.findByPosterOrderByIdDesc(user.getId());
    }
    @Override
    public Optional<Post> getPosts(long id) {
        return postRepository.findById(id);
    }
    @Override
    public List<Post> getPosts(int maxPosts, long userid, Collection<User> friends, String order) {
        //holt alle Posts aus der Datenbank (verbesserungswürdig)
        Iterable<Post> postIterable = postRepository.findAllByOrderByIdDesc();
        List<Post> posts = new ArrayList<Post>();
        List<Post> postsCache = new ArrayList<Post>();
        Post post;
        User user = new User();

        //show all - schreibt alle Posts ausser die eigenen in eine List
        if(friends == null) {
            for(Iterator<Post> postIterator = postIterable.iterator(); postIterator.hasNext(); ) {
                post = postIterator.next();
                if(post.getUser().getId() != userid) {  //eigene Posts werden nicht angezeigt
                    posts.add(post);
                }
            }
        }
        else { // show friends - die Posts müssen von Freunden sein
            for(Iterator<Post> postIterator = postIterable.iterator(); postIterator.hasNext(); ) {
                post = postIterator.next();
                if(post.getUser().getId() != userid) {  //eigene Posts werden nicht angezeigt
                    for (Iterator<User> userIterator = friends.iterator(); userIterator.hasNext(); ) {
                        user = userIterator.next();
                        if (post.getUser().getId() == user.getId()) { //falls der Post von einem Freund ist, wird er hinzugefügt
                            postsCache.add(post);
                        }
                    }
                }
            }
        }

        //falls nach Nicensteinen sortiert werden soll (nach Datum sortiert ist automatisch)
        if(order.equals("nice")) {
            //alle Posts außer die eigenen werden in eine Post-Collection geschrieben
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if(o1.getLikes().size() > o2.getLikes().size()) {
                        return -1;
                    }
                    return 1;
                }
            });
        }

        //limitiert auf die maxPosts Anzahl
        for(Iterator<Post> postIterator = postsCache.iterator(); maxPosts > 0 && postIterator.hasNext(); ) {
            posts.add(postIterator.next());
            maxPosts--;
        }

        return posts;
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
    public Likes createLike(Likes like) {
        Likes neu;
        if(likeRepository.findByLikerAndPost(like.getLiker(), like.getPost()) == null) {
            like.setCreationDate(new GregorianCalendar());
            neu = likeRepository.save(like);
        } else {
            neu = null;
        }
        return neu;
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
    public Likes getLike(Likes like) {
        like = likeRepository.findByLikerAndPost(like.getLiker(), like.getPost());
        return like;
    }



}
