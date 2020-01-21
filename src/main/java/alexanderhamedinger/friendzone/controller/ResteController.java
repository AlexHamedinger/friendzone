package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.PostServiceIF;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.GregorianCalendar;
import java.util.LinkedHashMap;

@RestController
@Scope("request")
public class ResteController {

    @Autowired
    private UserServiceIF userService;
    @Autowired
    private PostServiceIF postService;

    //Schnittstelle zu Parnershop - Hier kann ein externen Post erstellt werden
    @RequestMapping(value = "/restapi/post", method = RequestMethod.POST)
    public Post postExt(
            @RequestBody Object inputObject
    ) {
        System.out.println("Ich bin drin!");
        System.out.println("Der übergebene Post ist: ");
        String input = inputObject.toString();
        String [] emailArray = input.split(" email=");
        String email = emailArray[1].split(",")[0];
        String [] titleArray = input.split(" title=");
        String title = titleArray[1].split(",")[0];

        User user = userService.getUser("email", email);
        if(user == null) {
            return null;
        }
        //Erstellen des neuen Posts
        Post newpost = new Post();
        newpost.setPoster(user.getId());
        newpost.setUser(user);
        newpost.setTitle(title);
        newpost.setCreationDate(new GregorianCalendar());

        newpost = postService.createPost(newpost);
        System.out.println("Externen Post erstellt");

        Post returnpost = newpost;
        returnpost.setUser(null);

        return returnpost;
//        return ResponseEntity
//                .ok()
//                .body(post);
    }


    //Schnittstelle zu Parnershop - Hier kann ein externen Post erstellt werden
    @RequestMapping(value = "/restapi/posts", method = RequestMethod.POST)
    public Post createPost(
            @RequestBody Post post
    ) {
        User user = userService.getUser("email", post.getUser().getEmail());
        if(user == null) {
            return null;
        }
        //Erstellen des neuen Posts
        Post newpost = new Post();
        newpost.setPoster(user.getId());
        newpost.setUser(user);
        newpost.setTitle(post.getTitle());
        newpost.setCreationDate(new GregorianCalendar());
        newpost.setPostImage(post.getPostImage());

        newpost = postService.createPost(newpost);
        System.out.println("Externen Post erstellt");

        Post returnpost = newpost;
        returnpost.setUser(null);

        return returnpost;
//        return ResponseEntity
//                .ok()
//                .body(post);
    }
}
