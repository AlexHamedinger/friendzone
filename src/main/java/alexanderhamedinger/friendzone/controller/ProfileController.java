package alexanderhamedinger.friendzone.controller;

import alexanderhamedinger.friendzone.entities.User;
import alexanderhamedinger.friendzone.service.UserServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;

@Controller
@Scope("request")
public class ProfileController {

    @Autowired
    private UserServiceIF userService;


    @RequestMapping("/profile")
    public String profile(
            @RequestParam(required = false, name = "edited", defaultValue = "") String edited,
            @RequestParam(required = false, name = "imagefile") MultipartFile file,
            Principal prince,
            Model model
    ) {
        //User "Initialisierung"
        User user = userService.getUser("username", prince.getName());

        //profile?edited=image
        //Falls der User ein neues Profilfoto hochgeladen hat
        if(edited.equals("image")){
            //save user-image
            try {
                int img_length = file.getBytes().length;
                if(img_length > 1000) {
                    byte[] byteObjects = new byte[img_length];
                    int i = 0;
                    for (byte b : file.getBytes()) {
                        byteObjects[i++] = b;
                    }
                    user.setProfileImage(byteObjects);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //TODO: E-Mail, Usernamen und Passwort ändern implementieren

        //abschließende Model-Vorbereitungen
        {
            user = userService.save(user);
            model.addAttribute("user", user);
            return "profile/profile";
        }
    }
}
