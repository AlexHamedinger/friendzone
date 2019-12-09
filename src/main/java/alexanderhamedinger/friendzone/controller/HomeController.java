package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    private UserServiceIF userService;

    @RequestMapping("/")
    public String start() {
        return "home";
    }

    @RequestMapping("/home")
    public String home() {
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

    @RequestMapping("/register")
    public String register(
            @ModelAttribute("email") String email,
            @ModelAttribute("username") String username,
            @ModelAttribute("password") String password,
            Model model
    ) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setLatestRegistration(new Date());
        user.setInitialRegistration(new Date());
        user = userService.createUser(user);
        System.out.println(user);
        return "home";
    }

}
