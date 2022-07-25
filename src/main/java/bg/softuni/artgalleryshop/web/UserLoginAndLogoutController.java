package bg.softuni.artgalleryshop.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserLoginAndLogoutController {

    @GetMapping("/login")
    public String login() {
        return "auth-login";
    }

    //Note: this should be post mapping
    @PostMapping("/users/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY", username);
        redirectAttributes.addAttribute("bad_credentials", true);

        return "redirect:login";
    }
    @PostMapping("logout")
    public String logout(){
        return "/";
    }
}
