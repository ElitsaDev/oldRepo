package bg.softuni.artgalleryshop.init;

import bg.softuni.artgalleryshop.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDataBase implements CommandLineRunner {
    private final AuthService authService;

    public InitDataBase(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {
        //Initialization of user roles
        authService.initializeRoles();


    }
}
