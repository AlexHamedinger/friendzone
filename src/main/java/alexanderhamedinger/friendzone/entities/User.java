package alexanderhamedinger.friendzone.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String email;
    @NotEmpty(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotEmpty(message = "username is required")
    private String password;
    private byte[] profileImage;
    @Column(name = "initialregistration")
    private Date initialRegistration;
    @Column(name = "latestregistration")
    private Date latestRegistration;

    //getter & setter
    public long getUserID() {
        return userID;
    }
    public void setUserID(long userID) {
        this.userID = userID;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String eMail) {
        this.email = eMail;
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

    //UserDetails Override
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    //Override lang
    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        final User other = (User) o;
        if(!Objects.equals(userID, other.userID)) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        if(userID == (Long)null) {
            return 0;
        } else {
            return Long.hashCode(userID);
        }
    }
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
        return "#########################################################################\n" +
                "UserID: " + userID + "\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "EMail: " + email + "\n" +
                "Latest Registration: " + dateFormat.format(latestRegistration) + "\n" +
                "Member since: " + dateFormat.format(initialRegistration) + "\n" +
                "#########################################################################\n";
    }
}
