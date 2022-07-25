package bg.softuni.artgalleryshop.model.dto;

import javax.validation.constraints.*;

public class UserRegistrationDTO {
    @NotNull
    @Size(min = 5, max = 20)
    private String password;
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;

    @NotNull
    @Size(min = 5, max = 20)
    private String fullName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 20)
    private String confirmPassword;

    public UserRegistrationDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
