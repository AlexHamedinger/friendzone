package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;

//Die Tabelle muss "Likes" statt "Like" heissen da es sonst zu Problemen beim Erstellen der Tabelle kommt "CREATE TABLE LIKE..."
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

    //methoden
    public boolean hasId() {
        Long id = new Long(this.getId());
        if(!id.equals(null)) {
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return liker + " likes " + post;
    }
}
