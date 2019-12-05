package alexanderhamedinger.friendzone.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long likeID;
    private long liker;
    private long post;
    private Date creationDate;

    //getter & setter
    public long getLikeID() {
        return likeID;
    }
    public void setLikeID(long likeID) {
        this.likeID = likeID;
    }
    public long getLiker() {
        return liker;
    }
    public void setLiker(long liker) {
        this.liker = liker;
    }
    public long getPost() {
        return post;
    }
    public void setPost(long post) {
        this.post = post;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
