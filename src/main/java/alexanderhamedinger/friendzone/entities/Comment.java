package alexanderhamedinger.friendzone.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentID;
    private long commenter;
    private long commentedPost;
    private String text;
    private Date creationDate;

    //getter & setter
    public long getCommentID() {
        return commentID;
    }
    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }
    public long getCommenter() {
        return commenter;
    }
    public void setCommenter(long commenter) {
        this.commenter = commenter;
    }
    public long getCommentedPost() {
        return commentedPost;
    }
    public void setCommentedPost(long commentedPost) {
        this.commentedPost = commentedPost;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
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
        final Comment other = (Comment) o;
        if(!Objects.equals(commentID, other.commentID)) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        if(commentID == (Long)null) {
            return 0;
        } else {
            return Long.hashCode(commentID);
        }
    }
}
