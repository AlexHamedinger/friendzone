package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Friend;
import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.PostService;
import alexanderhamedinger.friendzone.service.PostServiceIF;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserServiceIF userService;
    @Autowired
    private PostServiceIF postService;


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
            model.addAttribute("invalidUsername", "Ungültiger Username! Der Username ist bereits vergeben.");
            return "user/register";
        } else {
            System.out.println("Created User: \n" + user);
            model.addAttribute("successfully_registered", "Du hast dich erfolgreich bei FriendZone registriert!");
            return "user/login";
        }
    }

    //zeigt das User-Profil eines anderen Users
    @RequestMapping("/user")
    public String user(
            @RequestParam(required = true, name = "id") int id,
            Model model,
            Principal prince
    ) {
        User otherUser = userService.getUserById((long) id).get();  //TODO: Unwrap Optional
        Collection<Post> posts = postService.getPostsByPoster(otherUser.getUsername());

        model.addAttribute("otherUser", otherUser);
        model.addAttribute("user", userService.findbyUsername(prince.getName()));
        model.addAttribute("posts", posts);
        return "user/user";
    }

    //zeigt die Freundesliste
    @RequestMapping("/friendsList")
    public String friends(
        Model model,
        Principal prince
    ) {
        User user = userService.findbyUsername(prince.getName());
        Collection<User> friends = user.getFriends();

        model.addAttribute("friends", friends);
        model.addAttribute("user", user);
        return "user/friendsList";
    }


    //befreundet zwei User
    @RequestMapping(value = "/friends/{otherid}", method = RequestMethod.GET)
    public ResponseEntity<Friend> becomeFriends(
            @PathVariable("otherid") int otherid,
            Principal prince
    ) {
        String response = "";
        User user = userService.findbyUsername(prince.getName());
        User otherUser = userService.getUserById((long)otherid).get(); //TODO: Unwrap Optional
        Friend friend = new Friend();

        if(user.hasFriend(otherUser)) {
            user.removeFriend(otherUser);
            friend = userService.findFriendByIds(user.getId(), otherUser.getId());
            userService.deleteFriend(friend);
            response = user.getUsername() + " unfriended " + otherUser.getUsername();
        } else {
            friend.setUser(user.getId());
            friend.setFriend(otherUser.getId());
            friend.setCreationDate(new GregorianCalendar());
            friend = userService.createFriend(friend);
            user.addFriend(otherUser);
            response = user.getUsername() + " befriended " + otherUser.getUsername();
        }

        System.out.println(response);
        user = userService.save(user);

        return ResponseEntity
                .ok()
                .body(friend);

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

