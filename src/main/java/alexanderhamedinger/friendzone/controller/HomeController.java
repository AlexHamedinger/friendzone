package alexanderhamedinger.friendzone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String register() {
        return "home";
    }
}
