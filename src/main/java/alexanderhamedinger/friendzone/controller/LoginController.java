package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.Date;

@Controller
public class LoginController {

    @Autowired
    private UserServiceIF userService;

    @RequestMapping("/")
    public String start(
            Principal principal
    ) {
        userService.setLatestRegistrationDate(principal.getName());
        System.out.println("User " + principal.getName() + " wurde eingeloggt. ");

        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("/login/register")
    public String loginRegister() {
        return "user/register";
    }

    @RequestMapping("/register")
    public String register(
            @ModelAttribute("email") String email,
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            Model model
    ) {
        System.out.println("Trying to register new User...");
        //neuen User anlegen und ins Repository schreiben
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setLatestRegistration(new Date());
        user.setInitialRegistration(new Date());
        user = userService.createUser(user);

        //Falls der Username bereits existiert wird null returned
        if(user == null) {
            System.out.println("Could not create User. Username " + username + " was already taken!");
            model.addAttribute("invalidUsername", "Invalid Username! Username already in use.");
            return "user/register";
        } else {
            System.out.println("Created User: \n" + user);
            model.addAttribute("successfully_registered", "You registered successfully to FriendZone!");
            return "user/login";
        }
    }

}
