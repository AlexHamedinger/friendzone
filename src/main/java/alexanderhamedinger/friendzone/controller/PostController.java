package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.service.PostServiceIF;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostServiceIF postService;
    @Autowired
    private UserServiceIF userService;

    @RequestMapping("/newPost")
    public String newpost() {

        return "newPost";
    }

    @RequestMapping("/home")
    public String home(
            Model model,
            Principal prince,
            @RequestParam (required = false, name = "action", defaultValue = "") String action,
            @ModelAttribute("titel") String titel,
            @RequestParam("imagefile") MultipartFile file)
    {

        //########### NEWPOST ############
        //Zum Erstellen eines neuen Posts
        if(action.equals("newpost")){
            long userID = userService.findIdByUsername(prince.getName());
            Post post = new Post();
            post.setPoster(userID);
            post.setTitle(titel);
            post.setCreationDate(new Date());
            //save image
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
            System.out.println("\nUser " + prince.getName() + " created new Post:" + post);

        }
        //#############################

        Collection<Post> posts = postService.getPostsByPoster(prince.getName());
        model.addAttribute("posts", posts);

        return "home";
    }


    @RequestMapping(value = "/postimages/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getPostImage(
            @PathVariable("id") int id
    ) throws IOException {
        Optional<Post> optionalPost = postService.getPostById(id);
        Post post;
        byte[] bytes = new byte[0];
        if(optionalPost.isPresent()) {
            post = optionalPost.get();
            bytes = post.getPostImage();
            System.out.println(post);
        } else {
            //TO-DO: Ersatzbild anzeigen
        }
        
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}
