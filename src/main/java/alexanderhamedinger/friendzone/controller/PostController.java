package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.Comment;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.xml.bind.PrintConversionEvent;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

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
        User user = userService.getUser("username", prince.getName());
        //neuen Kommentar erstellen
        if(!commentText.equals("")) {
            Comment comment = new Comment();
            comment.setCommenter(user.getId());
            comment.setUser(user);
            comment.setCommentedPost(post.getId());
            comment.setText(commentText);
            comment.setCreationDate(new GregorianCalendar());
            comment = postService.createComment(comment);
            System.out.println(comment);
        }

        List<Comment> comments = postService.getComments(post.getId());
        //abschließende Model-Vorbereitungen
        {
            model.addAttribute("user", user);
            model.addAttribute("post", post);
            model.addAttribute("comments", comments);
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
        User user = userService.getUser("username", prince.getName());
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

    //Erstellt oder löscht einen neuen Like zur angegebenen Post-Id
    @RequestMapping(value = "/likes/{postid}", method = RequestMethod.GET)
    public ResponseEntity<Likes> likes(
            @PathVariable("postid") int postid,
            Principal prince
    ) {
        User user = userService.getUser("username", prince.getName());
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

    //Löscht einen Kommentar zur angegebenen Comment-Id
    @RequestMapping(value = "/deleteComment/{commentID}", method = RequestMethod.GET)
    public ResponseEntity<Comment> deleteComment(
            @PathVariable("commentID") int commentID,
            Principal prince
    ) {
        Comment comment = postService.getComment(commentID);
        postService.deleteComment(commentID);
        System.out.println("Comment " + comment.getId() + " deleted.");
        //der Like wird zurückgegeben
        return ResponseEntity
                .ok()
                .body(comment);
    }


    //Schnittstelle zu Parnershop - Hier kann ein externen Post erstellt werden
    @RequestMapping(value = "/postextern/{email}/{title}", method = RequestMethod.GET)
    public ResponseEntity<Post> postExt(
            @PathVariable("title") String title,
            @PathVariable("email") String email,
            @RequestParam(required = false, name = "imagefile") MultipartFile file
    ) {
        User user = userService.getUser("email", email);
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
