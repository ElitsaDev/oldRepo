package bg.softuni.artgalleryshop.service;

import bg.softuni.artgalleryshop.model.dto.UserRegistrationDTO;
import bg.softuni.artgalleryshop.model.entity.UserEntity;
import bg.softuni.artgalleryshop.model.entity.UserRoleEntity;
import bg.softuni.artgalleryshop.model.enums.UserRoleEnum;
import bg.softuni.artgalleryshop.repository.UserRepository;
import bg.softuni.artgalleryshop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
//    private final EmailService emailService;

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public AuthService(UserRepository userRepository,
                       UserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder,
//                       EmailService emailService,
                       UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
//        this.emailService = emailService;
        this.userRoleRepository = userRoleRepository;

    }
    public void registerAndLogin(UserRegistrationDTO registrationDTO,
                                 Locale preferredLocale) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

        Optional<UserEntity> byEmail = this.userRepository.findByEmail(registrationDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("email.used");
        }

        UserEntity user = new UserEntity(
                registrationDTO.getUsername(),
                registrationDTO.getFullName(),
                registrationDTO.getEmail(),
                registrationDTO.getPassword());

        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        if(userRepository.count()==0){
            user.setUserRoles(List.of(userRoleRepository.findByUserRole(UserRoleEnum.ADMIN).orElseThrow()));
        } else{
            user.setUserRoles(List.of(userRoleRepository.findByUserRole(UserRoleEnum.USER).orElseThrow()));
        }

        this.userRepository.save(user);
        login(user);
//        emailService.sendRegistrationEmail(user.getEmail(),
//                user.getFullName(),
//                preferredLocale);
    }
    private void login(UserEntity userEntity){
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(userEntity.getUsername());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().getAuthentication();
    }

    public void initializeRoles() {
        if(userRoleRepository.count()!=0){
            return;
        }

        Arrays.stream(UserRoleEnum.values()).map(enumRole ->{
                    UserRoleEntity role = new UserRoleEntity();
                    role.setUserRole(enumRole);
                    return role;
                }).forEach(userRoleRepository::save);
    }
}



