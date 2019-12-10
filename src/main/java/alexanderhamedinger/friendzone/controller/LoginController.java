package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;

@Controller
public class LoginController {

    @Autowired
    private UserServiceIF userService;

    @RequestMapping("/")
    public String start() {
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login/register")
    public String loginRegister() {
        return "register";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/register")
    public String register(
            @ModelAttribute("email") String email,
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            Model model
    ) {
        //neuen User anlegen und ins Repository schreiben
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setLatestRegistration(new Date());
        user.setInitialRegistration(new Date());
        user = userService.createUser(user);
        System.out.println(user);

        //Falls der Username bereits existiert wird null returned
        if(user == null) {
            model.addAttribute("invalidUsername", "Invalid Username!");
            return "register";
        } else {
            model.addAttribute("successfully_registered", "You registered successfully to FriendZone!");
            model.addAttribute("username", user.getUsername());
            model.addAttribute("password", password);
            return "login";
        }
    }

}
