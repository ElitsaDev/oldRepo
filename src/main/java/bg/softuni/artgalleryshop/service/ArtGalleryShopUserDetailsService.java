package bg.softuni.artgalleryshop.service;

import bg.softuni.artgalleryshop.model.entity.UserEntity;
import bg.softuni.artgalleryshop.model.entity.UserRoleEntity;
import bg.softuni.artgalleryshop.model.user.ArtGalleryShopUserDetails;
import bg.softuni.artgalleryshop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ArtGalleryShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ArtGalleryShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //This is only for login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(this::map)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User with username " + username + " not found!"));
    }

    private UserDetails map(UserEntity userEntity) {
        return User.builder().
                username(userEntity.getUsername()).
                password(userEntity.getPassword()).
                authorities(userEntity.
                                getUserRoles().
                                stream().
                                map(this::map).
                                toList())
                .build();
    }
    private GrantedAuthority map(UserRoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" +
                userRole.
                        getUserRole().name());
    }
}
