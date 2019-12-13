package alexanderhamedinger.friendzone.entities;

import alexanderhamedinger.friendzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postID;
    private long poster;
    private String title;
    @Lob
    private Byte[] postImage;
    @Column(name = "creationdate")
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
    public Byte[] getPostImage() {
        return postImage;
    }
    public void setPostImage(Byte[] postImage) {
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
        if(postID == 0L) {
            return 0;
        } else {
            return (int)postID;
        }
    }
    @Override
    public String toString() {
        String y_n = "NO";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
        if(postImage != null) {
            y_n = "YES";
        }
        return "\n#########################################################################\n" +
                "PostID: " + postID + "\n" +
                "Posted by: " + poster + "\n" +
                "Title: " + title + "\n" +
                "Image (Y/N): " + y_n + "\n" +
                "Date: " + dateFormat.format(creationDate) + "\n" +
                "#########################################################################\n";
    }
}
