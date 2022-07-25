package bg.softuni.artgalleryshop.web;

import bg.softuni.artgalleryshop.model.dto.UserRegistrationDTO;
import bg.softuni.artgalleryshop.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserRegisterController {
    private AuthService authService;
    private LocaleResolver localeResolver;


    public UserRegisterController(AuthService authService,
                                  LocaleResolver localeResolver) {
        this.authService = authService;
        this.localeResolver = localeResolver;
    }

    @ModelAttribute("userRegistrationDTO")
    public UserRegistrationDTO initForm() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO userRegistrationDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest servletRequest){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO",
                    bindingResult);
            return "redirect:/users/register";
        }
        this.authService.registerAndLogin(
                userRegistrationDTO,
                localeResolver.resolveLocale(servletRequest));

        return "redirect:/";
    }
}
