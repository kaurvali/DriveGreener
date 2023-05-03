package ee.ut.cs.drivegreener.login.payload.response;

// based off https://www.bezkoder.com/angular-15-spring-boot-jwt-auth/

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

