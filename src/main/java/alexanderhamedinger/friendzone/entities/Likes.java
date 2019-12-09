package alexanderhamedinger.friendzone.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Likes {

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

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        final Likes other = (Likes) o;
        if(!Objects.equals(likeID, other.likeID)) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        if(likeID == (Long)null) {
            return 0;
        } else {
            return Long.hashCode(likeID);
        }
    }
}
