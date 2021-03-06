package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Post extends BasicEntity {

    private long poster;
    @ManyToOne
    private User user;
    private String title;
    @Lob
    private byte[] postImage;
    @OneToMany
    private Collection<Likes> likes;

    //getter & setter
    public long getPoster() {
        return poster;
    }
    public void setPoster(long poster) {
        this.poster = poster;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public byte[] getPostImage() {
        return postImage;
    }
    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }
    public Collection<Likes> getLikes() {
        return likes;
    }
    public void setLikes(Collection<Likes> likes) {
        this.likes = likes;
    }

    //methoden
    //getURL-Methoden: um verschiedene "href" oder "src" in den Templates mit sinnvollen Strings zu befüllen
    public String getImageURL() {
        return "/postimages/" + this.getId();
    }
    public String getDetailURL() {
        return "/postDetail?id=" + this.getId();
    }
    public String getLikeURL() {
        return "http://localhost:1889/likes/"+this.getId();
    }
    public String getDeleteURL() {
        return "/delete?post=" + this.getId();
    }
    public String getFinallyDeleteURL() {
        return "/home?action=deletePost"+this.getId();
    }
    public String getUserImageURL() {
        return "/userimages/" + poster;
    }

    //Methoden auf die Likes-Collection
    public void addLike(Likes like) {
        this.likes.add(like);
    }
    public void removeLike(Likes like) {
        this.likes.remove(like);
    }
    public int getNumberOfLikes() {
        int size = 0;
        if(likes != null) {
            if(!likes.isEmpty()) {
                size = likes.size();
            }
        }
        return size;
    }

    //wird benötigt damit die like-Buttons richtig dargestellt werden
    public String likeOrUnlike(Likes like) {
        String likeOrUnlike = "Nicenstein";
        if(likes == null) {
            return likeOrUnlike;
        }
        if(likes.contains(like)) {
            likeOrUnlike = "Nicenstein ";
        }
        return likeOrUnlike;
    }
    //Damit der Löschen-Button auf der Post-Detail Seite angezeigt wird
    public String isMyOwnPost(User user) {
        String answer = "notMine";
        if(user.getId() == poster) {
            answer = "mine";
        }
        return answer;
    }

    @Override
    public String toString() {

        return "\n#########################################################################\n" +
                "PostID: " + this.getId() + "\n" +
                "Posted by: " + poster + "\n" +
                "Title: " + title + "\n" +
                "Date: " + this.getCreationDateAsString() + "\n" +
                "Likes: " + this.getNumberOfLikes() + "\n" +
                "#########################################################################\n";
    }
}
