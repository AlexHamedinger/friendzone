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
import java.util.*;

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
        //TODO: Der User Darf nicht "Nice", "Username" oder "EMail" heissen
        //neuen User anlegen und ins Repository schreiben
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setLatestRegistration(new GregorianCalendar());
        user.setCreationDate(new GregorianCalendar());
        user = userService.createUser(user);

        //Falls der Username bereits existiert
        if(user.getUsername() == "Username") {
            System.out.println("Could not create User. Username " + username + " was already taken!");
            model.addAttribute("invalidInput", "Ungültiger Username! Der Username ist bereits vergeben.");
            return "user/register";
        } else if(user.getUsername() == "EMail") {
            System.out.println("Could not create User. E-Mail " + email + " is already in use!");
            model.addAttribute("invalidInput", "Diese E-Mail Adresse wird bereits verwendet!");
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
        Principal prince,
        @RequestParam(required = false, name = "show", defaultValue = "friends") String show
    ) {
        User user = userService.findbyUsername(prince.getName());
        List<User> friends = new ArrayList<User>();

        //je nach Auswahl-Option wird die friends-List anders befüllt
        if(show.equals("friends")) {
            friends = (List<User>) userService.getRealUserFriends(user.getId());
        }
        else if(show.equals("mine")) {
            friends = (List<User>) user.getFriends();
            //User mit denen man bereits befreundet ist werden nicht angezeigt
            Collection<User> toDelete = userService.getRealUserFriends(user.getId());
            for(Iterator<User> i = toDelete.iterator(); i.hasNext(); ) {
                friends.remove(i.next());
            }
        }
        else if(show.equals("theirs")) {
            Collection<Friend> friendsCollection = userService.getFriendByFriend(user.getId());
            for (Iterator<Friend> i = friendsCollection.iterator(); i.hasNext(); ) {
                friends.add(userService.getUserById(i.next().getUser()).get());
            }
            //User mit denen man bereits befreundet ist werden nicht angezeigt
            Collection<User> toDelete = userService.getRealUserFriends(user.getId());
            for(Iterator<User> i = toDelete.iterator(); i.hasNext(); ) {
                friends.remove(i.next());
            }
        }

        //die List wird alphabetisch sortiert
        Collections.sort(friends, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getUsername().toLowerCase().compareTo(u2.getUsername().toLowerCase());
            }
        });

        model.addAttribute("message", createFriendsMessage(friends.size(), show));
        model.addAttribute("friends", friends);
        model.addAttribute("user", user);
        return "user/friendsList";
    }

    //verarbeitet Suchanfragen
    @RequestMapping("/search")
    public String search(
        Model model,
        Principal prince,
        @ModelAttribute("searchQuery") String searchQuery
        ) {
        User user = userService.findbyUsername(prince.getName());
        List<User> searchResult = new ArrayList<User>();

        //Die Suchfunktion soll nur ausgeführt werden wenn auch ein Suchstring eingegeben wurde, sonst werden alle User ausgegeben
        if(!searchQuery.equals("")) {
            searchResult = userService.findUserLikeUsername(searchQuery);
        } else {
            searchResult = userService.getAll();
        }

        //Man soll nicht nach sich selbst suchen können
        if(searchResult.contains(user)) {
            searchResult.remove(user);
        }

        if(searchResult.isEmpty()) {
            model.addAttribute("message", "Zu Ihrer Suchanfrage gab es leider keinen passenden User.");
        }
        if(!searchResult.isEmpty()) {
            //die Liste wird alphabetisch sortiert
            Collections.sort(searchResult, new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    return u1.getUsername().toLowerCase().compareTo(u2.getUsername().toLowerCase());
                }
            });
            model.addAttribute("results", searchResult);
        }
        model.addAttribute("user", user);
        return "/user/search";
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



    //Hilfsfunktion für die Freundes Liste
    //Je nach Auswhal und Größe der Freundes Liste wird eine andere Message zurückgeliefert
    private String createFriendsMessage(int size, String show) {
        String message = "";

        if(show.equals("friends")) {
            if(size == 0) {
                message = "Du hast noch keine Freunde! Freunde sind User, die du zu deiner Liste hinzugefügt hast und die auch dich in ihre Liste hinzugefügt haben.";
            } else if(size == 1) {
                message = "Du hast 1 Freund! Freunde sind User, die du zu deiner Liste hinzugefügt hast und die auch dich in ihre Liste hinzugefügt haben.";
            } else {
                message = "Du hast " + size + " Freunde! Freunde sind User, die du zu deiner Liste hinzugefügt hast und die auch dich in ihre Liste hinzugefügt haben.";
            }
        }
        else if(show.equals("mine")) {
            if(size == 0) {
                message = "Du folgts noch keinem anderen User. Füge User deiner Freundesliste hinzu, um sie hier zu sehen!";
            } else if(size == 1) {
                message = "Du folgts 1 anderen User. Sobald er/sie/es dich Ihrer Liste hinzugefügt hat, seid ihr Freunde!";
            } else {
                message = "Du folgts " + size + " anderen Usern. Sobald sie dich Ihrer Liste hinzugefügt haben, seid ihr Freunde!";
            }
        }
        else if(show.equals("theirs")) {
            if(size == 0) {
                message = "Dir folgt noch kein anderer User. Poste etwas, um auf dich aufmerksam zu machen!";
            } else if(size == 1) {
                message = "Dir folgt 1 anderer User. Sobald du ihn/sie/es in deine Liste hinzugefügt hast, seid ihr Freunde!";
            } else {
                message = "Dir folgen " + size + " andere User. Sobald du sie in deine Liste hinzugefügt hast, seid ihr Freunde!";
            }
        }

        return message;
    }
}

