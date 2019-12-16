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
@AttributeOverride(name = "creationDate", column = @Column(name = "initialregistration"))
public class User extends BasicEntity implements UserDetails {

    private String email;
    @NotEmpty(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotEmpty(message = "username is required")
    private String password;
    @Lob
    private Byte[] profileImage;
    @Column(name = "latestregistration")
    private Date latestRegistration;

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
    public Byte[] getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(Byte[] profileImage) {
        this.profileImage = profileImage;
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



    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
        return "\n#########################################################################\n" +
                "UserID: " + this.getId() + "\n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n" +
                "EMail: " + email + "\n" +
                "Latest Registration: " + dateFormat.format(latestRegistration) + "\n" +
                "Member since: " + dateFormat.format(this.getCreationDate()) + "\n" +
                "#########################################################################\n";
    }
}
