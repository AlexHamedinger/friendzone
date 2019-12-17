package alexanderhamedinger.friendzone.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;

@Entity
@AttributeOverride(name = "creationDate", column = @Column(name = "initialregistration"))
public class User extends BasicEntity implements UserDetails {

    private String email;
    @NotEmpty(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotEmpty(message = "password is required")
    private String password;
    @Lob
    private byte[] profileImage;
    @Column(name = "latestregistration")
    private GregorianCalendar latestRegistration;

    //getter & setter
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
    public String getImageURL() {
        return "/userimages/" + this.getId();
    }
    public GregorianCalendar getLatestRegistration() {
        return latestRegistration;
    }
    public void setLatestRegistration(GregorianCalendar latestRegistration) {
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


    @Override
    public String toString() {
        String y_n = "NO";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
        if(profileImage != null) {
            y_n = "YES";
        }
        return "\n#########################################################################\n" +
                "UserID: " + this.getId() + "\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "EMail: " + email + "\n" +
                "Latest Registration: " + dateFormat.format(latestRegistration.getTime()) + "\n" +
                "Member since: " + dateFormat.format(this.getCreationDate().getTime()) + "\n" +
                "Profile Image: " + y_n + "\n" +
                "#########################################################################\n";
    }
}
