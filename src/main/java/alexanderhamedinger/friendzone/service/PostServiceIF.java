package alexanderhamedinger.friendzone.service;

import alexanderhamedinger.friendzone.entities.Comment;
import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostServiceIF {

    //Methoden auf die USER-Entity
    //Create
    public Post createPost(Post post);
    //Read
    public Collection<Post> getPosts(String username);
    public Post getPosts(long id);
    public List<Post> getPosts(int maxPosts, long userid, Collection<User> friends, String order);
    //Update
    public Post save(Post post);
    //Delete
    public void deletePost(long id);

    //Methoden auf die LIKES-Entity
    //Create
    public Likes createLike(Likes like);
    //Read
    public Likes getLike(Likes like);
    //Delete
    public void deleteLike(Likes like);

    //Methoden auf die COMMENT-Entity
    //Create
    public Comment createComment(Comment comment);
    //Read
    public Comment getComment(long commentID);
    public List<Comment> getComments(long postID);
    //Delete
    public void deleteComment(long commentID);
    public void deletePostComments(long postID);

}
