package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long friendID;
    private long user;
    private long friend;
    @Column(name = "friendssince")
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

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        final Friend other = (Friend) o;
        if(!Objects.equals(friendID, other.friendID)) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        if(friendID == 0L) {
            return 0;
        } else {
            return (int)friendID;
        }
    }
}
