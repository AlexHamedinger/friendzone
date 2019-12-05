package alexanderhamedinger.friendzone.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postID;
    private long poster;
    private String title;
    private byte[] postImage;
    private Date creationDate;

    //getter & setter
    public long getPostID() {
        return postID;
    }
    public void setPostID(long postID) {
        this.postID = postID;
    }
    public long getPoster() {
        return poster;
    }
    public void setPoster(long poster) {
        this.poster = poster;
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
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        final Post other = (Post) o;
        if(!Objects.equals(postID, other.postID)) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        if(postID == (Long)null) {
            return 0;
        } else {
            return Long.hashCode(postID);
        }
    }
}
