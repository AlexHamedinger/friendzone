package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.service.PostServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostServiceIF postService;

    @RequestMapping("/newPost")
    public String newpost() {
        return "post/newPost";
    }

    @RequestMapping("/postDetail")
    public String postdetail(
            @RequestParam(required = false, name = "id", defaultValue = "") String id,
            Model model
    ) {
        //get Post-Data
        Optional<Post> optionalPost = postService.getPostById(Long.parseLong(id));
        Post post;
        if(optionalPost.isPresent()) {
            post = optionalPost.get();
        } else {
            model.addAttribute("message", "Der Post konnte nicht mehr gefunden werden");
            return "error";
        }

        //TO-DO: Wenn es dein eigener Post ist, "Post löschen" Button anzeigen



        //abschließende Model-Vorbereitungen
        model.addAttribute("post", post);
        return "post/postDetail";
    }

    //Zeigt das Post Image der eingegebenen Id an
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
        } else {
            //TO-DO: Ersatzbild anzeigen
        }
        
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}
