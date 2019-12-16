package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;
import java.text.SimpleDateFormat;

@Entity
public class Post extends BasicEntity {

    private long poster;
    private String title;
    @Lob
    private Byte[] postImage;

    //getter & setter
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

    @Override
    public String toString() {
        String y_n = "NO";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
        if(postImage != null) {
            y_n = "YES";
        }
        return "\n#########################################################################\n" +
                "PostID: " + this.getId() + "\n" +
                "Posted by: " + poster + "\n" +
                "Title: " + title + "\n" +
                "Image (Y/N): " + y_n + "\n" +
                "Date: " + dateFormat.format(this.getCreationDate()) + "\n" +
                "#########################################################################\n";
    }
}
