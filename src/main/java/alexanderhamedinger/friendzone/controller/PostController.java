package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;
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
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostServiceIF postService;
    @Autowired
    private UserServiceIF userService;

    @RequestMapping("/newPost")
    public String newpost() {
        return "post/newPost";
    }

    @RequestMapping("/postDetail")
    public String postdetail(
            @RequestParam(required = false, name = "id", defaultValue = "") String id,
            Principal prince,
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
        model.addAttribute("user", userService.findbyUsername(prince.getName()));
        model.addAttribute("post", post);
        return "post/postDetail";
    }

    @RequestMapping("/delete")
    public String postDelete(
            @RequestParam(required = false, name = "post", defaultValue = "") String postid,
            Model model
    ) {
        Optional<Post> post = postService.getPostById(Long.parseLong(postid));

        model.addAttribute("post", post.get());
        return "post/postDelete";
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

    //Erstellt neue Likes
    @RequestMapping(value = "/likes/{postid}", method = RequestMethod.GET)
    public ResponseEntity<Likes> likes(
            @PathVariable("postid") int postid,
            Principal prince
    ) {
        User user = userService.findbyUsername(prince.getName());

        Optional<Post> optionalPost = postService.getPostById(postid);
        Post post = null;
        if(optionalPost.isPresent()) {
            post = optionalPost.get();
        }

        Likes like = new Likes();
        like.setLiker(user.getId());
        like.setPost(postid);

        if(postService.isLikeUnique(like)) {
            like = postService.createLike(like);
            post.addLike(like);
            post = postService.save(post);
            user.addLike(like);
            user = userService.save(user);
            System.out.println(like);
        } else {
            like = postService.getCompleteLike(like);
            post.removeLike(like);
            post = postService.save(post);
            user.removeLike(like);
            user = userService.save(user);
            postService.deleteLike(like);
            System.out.println(like + " deleted");
        }

        return ResponseEntity
                .ok()
                .body(like);
    }
}
