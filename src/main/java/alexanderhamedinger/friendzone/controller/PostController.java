package alexanderhamedinger.friendzone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PostController {

    @RequestMapping("/newPost")
    public String newpost() {

        return "newPost";
    }

    @RequestMapping("/home")
    public String home(
            Model model,
            @RequestBody(required = false) String homeParameter
    ) {
        //Um den Parameter nach dem home?parameter zu ermitteln
        if(homeParameter != null) homeParameter = homeParameter.split("=")[0];

        if(homeParameter == "new"){
            //TO-DO
            //Post speichern
        }
        
        return "home";
    }
}
