package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;

@Entity
public class Friend extends BasicEntity {

    private long user;
    private long friend;

    //getter & setter
    public long getUser() {
        return user;
    }
    public void setUser(long user) {
        this.user = user;
    }
    public long getFriend() {
        return friend;
    }
    public void setFriend(long friend) {
        this.friend = friend;
    }

}
