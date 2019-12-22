package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserServiceIF userService;

    //Gibt die Login-Seite zurück
    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    //Gibt die neu-registrieren Seite zurück
    @RequestMapping("/login/register")
    public String loginRegister() {
        return "user/register";
    }

    //Verarbeitet die neue Registrierung
    @RequestMapping("/register")
    public String register(
            @ModelAttribute("email") String email,
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            Model model
    ) {
        //neuen User anlegen und ins Repository schreiben
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setLatestRegistration(new GregorianCalendar());
        user.setCreationDate(new GregorianCalendar());
        user = userService.createUser(user);

        //Falls der Username bereits existiert wird null returned
        if(user == null) {
            System.out.println("Could not create User. Username " + username + " was already taken!");
            model.addAttribute("invalidUsername", "Invalid Username! Username already in use.");
            return "user/register";
        } else {
            System.out.println("Created User: \n" + user);
            model.addAttribute("successfully_registered", "You registered successfully to FriendZone!");
            return "user/login";
        }
    }


    //Zeigt das User Profil Image der eingegebenen Id an
    @RequestMapping(value = "/userimages/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getPostImage(
            @PathVariable("id") int id
    ) throws IOException {
        Optional<User> optionalUser = userService.getUserById(id);
        User user;
        byte[] bytes = new byte[0];
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
            bytes = user.getProfileImage();
        } else {
            //TO-DO: Ersatzbild anzeigen
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

}

