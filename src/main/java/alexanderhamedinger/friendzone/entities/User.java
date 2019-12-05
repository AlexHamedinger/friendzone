package alexanderhamedinger.friendzone.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String eMail;
    private String username;
    private String password;
    private byte[] profileImage;
    private Date initialRegistration;
    private Date latestRegistration;

    //getter & setter
    public long getUserID() {
        return userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
    public String geteMail() {
        return eMail;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public byte[] getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
    public Date getInitialRegistration() {
        return initialRegistration;
    }
    public void setInitialRegistration(Date initialRegistration) {
        this.initialRegistration = initialRegistration;
    }
    public Date getLatestRegistration() {
        return latestRegistration;
    }
    public void setLatestRegistration(Date latestRegistration) {
        this.latestRegistration = latestRegistration;
    }
}
