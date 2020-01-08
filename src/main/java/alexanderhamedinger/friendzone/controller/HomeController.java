package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.PostServiceIF;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

@Controller
public class HomeController {

    @Autowired
    private UserServiceIF userService;
    @Autowired
    private PostServiceIF postService;


    //Home-Pfad, wird nach dem einloggen ausgeführt
    @RequestMapping("/")
    public String start(
            Principal principal,
            Model model
    ) {
        if(principal != null) {
            User user = userService.getUser(principal.getName());
            user.setLatestRegistration(new GregorianCalendar());
            userService.save(user);
            System.out.println("User " + principal.getName() + " wurde eingeloggt. " + user);
            model.addAttribute("posts", postService.getPosts(principal.getName()));
            model.addAttribute("user", userService.getUser(principal.getName()));
        }

        return "home";
    }

    //Hier wird die Startseite angezeigt
    //Es werden verschiedene Funktionen abgearbeitet, die danach auf die Startseite weiterleiten
    @RequestMapping("/home")
    public String home(
            Model model,
            Principal prince,
            @RequestParam(required = false, name = "action", defaultValue = "noaction") String action,
            @ModelAttribute("titel") String titel,
            @RequestParam(required = false, name = "imagefile") MultipartFile file)
    {
        //User "Initialisierung"
        User user = userService.getUser(prince.getName());

        //home?action=newpost
        //Es wird ein neuer Post erstellt
        if(action.equals("newpost")){
            //Erstellen des neuen Posts
            Post post = new Post();
            post.setPoster(user.getId());
            post.setUser(user);
            post.setTitle(titel);
            post.setCreationDate(new GregorianCalendar());
            //save post-image
            try {
                byte[] byteObjects = new byte[file.getBytes().length];
                int i = 0;
                for(byte b : file.getBytes()){
                    byteObjects[i++] = b;
                }
                post.setPostImage(byteObjects);

            } catch (IOException e) {
                e.printStackTrace();
            }

            post = postService.createPost(post);
            System.out.println("\nUser " + user.getUsername() + " created new Post:" + post);

        }

        //home?action=deletePost123
        //Ein Post wird gelöscht
        if(action.contains("deletePost")) {
            String postid = action.split("deletePost")[1];
            long id = Long.parseLong(postid);


            //Beim Post löschen zugehörige Likes löschen
            Post post = postService.getPosts(id);
            Collection<Likes> likes = post.getLikes();
            Likes like;
            User LikeUser;
            Collection<Likes> LikeUserLikes;
            Iterator<Likes> i = likes.iterator();
            while( i.hasNext() ) {
                like = (Likes) i.next();
                likes.remove(like);
                LikeUser = userService.getUser(like.getLiker());
                LikeUserLikes = LikeUser.getLikes();
                LikeUserLikes.remove(like);
                postService.deleteLike(like);
                i = likes.iterator();
            }

            //der Post wird gelöscht
            postService.deletePost(id);
            System.out.println("deleted Post " + id);
        }

        //abschließende Model-Vorbereitungen
        {
            user = userService.save(user);
            model.addAttribute("user", user);
            Collection<Post> posts = postService.getPosts(user.getUsername());
            model.addAttribute("posts", posts);
            return "home";
        }
    }

}
