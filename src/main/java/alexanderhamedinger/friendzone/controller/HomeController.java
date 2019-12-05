package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class HomeController {

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
        return "home";
    }
}
