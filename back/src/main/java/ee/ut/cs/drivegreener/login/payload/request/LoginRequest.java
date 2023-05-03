package ee.ut.cs.drivegreener.login.payload.request;

import javax.validation.constraints.NotBlank;

// based off https://www.bezkoder.com/angular-15-spring-boot-jwt-auth/

public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
