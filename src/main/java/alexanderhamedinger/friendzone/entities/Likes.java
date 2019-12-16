package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Likes extends BasicEntity {

    private long liker;
    private long post;

    //getter & setter
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

}
