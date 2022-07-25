package bg.softuni.artgalleryshop.model.entity;

import bg.softuni.artgalleryshop.model.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name ="user_roles")
public class UserRoleEntity extends BaseEntity{

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    public UserRoleEntity() {
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public UserRoleEntity setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }
}
