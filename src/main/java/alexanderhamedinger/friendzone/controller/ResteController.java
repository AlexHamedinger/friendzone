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

@RestController
@Scope("request")
public class ResteController {

    @Autowired
    private UserServiceIF userService;
    @Autowired
    private PostServiceIF postService;

    //Schnittstelle zu Parnershop - Hier kann ein externen Post erstellt werden
    @RequestMapping(value = "/postextern/{email}/{title}", method = RequestMethod.POST)
    public ResponseEntity<?> postExt(
            @PathVariable("title") String title,
            @PathVariable("email") String email,
            @RequestParam(required = false, name = "imagefile") MultipartFile file
    ) {
        User user = userService.getUser("email", email);
        if(user == null) {
            return ResponseEntity
                    .ok()
                    .body("Ungültige E-Mail - Es existiert kein User mit der E-Mail Adresse " + email);
        }
        //Erstellen des neuen Posts
        Post post = new Post();
        post.setPoster(user.getId());
        post.setUser(user);
        post.setTitle(title);
        post.setCreationDate(new GregorianCalendar());

        //save post-image
        try {
            byte[] byteObjects = new byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
            post.setPostImage(byteObjects);

        } catch (Exception e) {
            e.toString();
        }

        post = postService.createPost(post);
        System.out.println(email +"\nExternen Post erstellt");

        return ResponseEntity
                .ok()
                .body(post);
    }
}
