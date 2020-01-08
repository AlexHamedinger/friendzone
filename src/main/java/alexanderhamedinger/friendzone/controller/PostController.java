package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Likes;
import alexanderhamedinger.friendzone.entities.Post;
import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.PostServiceIF;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostServiceIF postService;
    @Autowired
    private UserServiceIF userService;


    //Zum Laden der "neuen Pinnwandeintrag erstellen" Seite
    @RequestMapping("/newPost")
    public String newpost() {
        return "post/newPost";
    }


    //Post-Detail Seite wird angezeigt
    @RequestMapping("/postDetail")
    public String postdetail(
            @RequestParam(name = "id") String id,
            @RequestParam(required = false, name = "commentText", defaultValue = "") String commentText,
            Principal prince,
            Model model
    ) {
        //der Post zur angegebenen Id wird geladen
        Post post = postService.getPosts(Long.parseLong(id));
        //TODO: Kommentar erstellen und Kommentare zu jeweiligem Post übergeben


        //abschließende Model-Vorbereitungen
        {
            model.addAttribute("user", userService.getUser(prince.getName()));
            model.addAttribute("post", post);
            return "post/postDetail";
        }
    }


    //Verweist auf die "Diesen Post wirklich löschen" Seite
    @RequestMapping("/delete")
    public String postDelete(
            @RequestParam(required = false, name = "post", defaultValue = "") String postid,
            Model model
    ) {
        Post post = postService.getPosts(Long.parseLong(postid));

        model.addAttribute("post", post);
        return "post/postDelete";
    }

    //Verweist auf die "neueste Posts" Seite
    @RequestMapping("/latestPosts")
    public String latestPosts(
        Model model,
        Principal prince,
        @RequestParam(name = "show", defaultValue = "all") String show,
        @RequestParam(name = "order", defaultValue = "latest") String order
    ) {


        int maxPosts = 10;
        User user = userService.getUser(prince.getName());
        List<Post> posts = new ArrayList<Post>();

        if(show.equals("all")) {
            posts = postService.getPosts(10, user.getId(), null, order);
        } else if(show.equals("friends")) {
            Collection<User> friends = userService.getMutualFriends(user.getId());
            posts = postService.getPosts(maxPosts, user.getId(), friends, order);
        }


        model.addAttribute("posts", posts);
        model.addAttribute("user", user);
        model.addAttribute("loadMessage", show + "-" + order);
        //TODO: falls es noch keine Posts gibt, Platzhalter-Message anzeigen
        return "post/latestPosts";
    }


    //Zeigt das Post Image der eingegebenen Post-Id an
    @RequestMapping(value = "/postimages/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getPostImage(
            @PathVariable("id") int id
    ) throws IOException {
        Post post = postService.getPosts(id);
        byte[] bytes = new byte[0];

        bytes = post.getPostImage();

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }


    //Erstellt einen neuen Like zur angegebenen Post-Id
    @RequestMapping(value = "/likes/{postid}", method = RequestMethod.GET)
    public ResponseEntity<Likes> likes(
            @PathVariable("postid") int postid,
            Principal prince
    ) {
        User user = userService.getUser(prince.getName());
        Post post = postService.getPosts(postid);

        //neuer Like wird erstellt
        Likes like = new Likes();
        Likes createdLike;
        like.setLiker(user.getId());
        like.setPost(postid);
        createdLike = postService.createLike(like);

        //Like verarbeitung:
        //Falls der Post von diesem User bereits geliked war, wird er ge-unliked
        //Falls der Post von diesem User noch nicht geliked war, wird er geliked
        if(createdLike != null) {
            post.addLike(createdLike);
            post = postService.save(post);
            user.addLike(createdLike);
            user = userService.save(user);
            System.out.println(createdLike);
        }
        else {
            like = postService.getLike(like);
            post.removeLike(like);
            post = postService.save(post);
            user.removeLike(like);
            user = userService.save(user);
            postService.deleteLike(like);
            System.out.println(like + " deleted");
        }

        //der Like wird zurückgegeben
        return ResponseEntity
                .ok()
                .body(like);
    }


}
