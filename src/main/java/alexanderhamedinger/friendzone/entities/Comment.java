package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Comment extends BasicEntity {

    private long commenter;
    private long commentedPost;
    private String text;

    //getter & setter
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

}
