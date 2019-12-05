package alexanderhamedinger.friendzone.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long friendID;
    private long user;
    private long friend;
    private Date friendsSince;

    //getter & setter
    public long getFriendID() {
        return friendID;
    }
    public void setFriendID(long friendID) {
        this.friendID = friendID;
    }
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
    public Date getFriendsSince() {
        return friendsSince;
    }
    public void setFriendsSince(Date friendsSince) {
        this.friendsSince = friendsSince;
    }
}
