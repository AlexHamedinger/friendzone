package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.service.PostServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostServiceIF postService;

    @RequestMapping("/newPost")
    public String newpost() {
        return "post/newPost";
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
