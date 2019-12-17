package alexanderhamedinger.friendzone.controller;

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

@Controller
public class HomeController {

    @Autowired
    private UserServiceIF userService;

    @Autowired
    private PostServiceIF postService;


    @RequestMapping("/")
    public String start(
            Principal principal,
            Model model
    ) {
        if(principal != null) {
            userService.setLatestRegistrationDate(principal.getName());
            System.out.println("User " + principal.getName() + " wurde eingeloggt. ");
            model.addAttribute("posts", postService.getPostsByPoster(principal.getName()));
            model.addAttribute("user", userService.findbyUsername(principal.getName()));
        }

        return "home";
    }

    @RequestMapping("/home")
    public String home(
            Model model,
            Principal prince,
            @RequestParam(required = false, name = "action", defaultValue = "") String action,
            @ModelAttribute("titel") String titel,
            @RequestParam(required = false, name = "imagefile") MultipartFile file)
    {
        //User "Initialisierung"
        User user = userService.findbyUsername(prince.getName());

        //home?action=newpost
        if(action.equals("newpost")){
            //Erstellen des neuen Posts
            Post post = new Post();
            post.setPoster(user.getId());
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

        //abschließende Model-Vorbereitungen
        {
            user = userService.save(user);
            model.addAttribute("user", user);
            Collection<Post> posts = postService.getPostsByPoster(user.getUsername());
            model.addAttribute("posts", posts);
            return "home";
        }
    }
}
