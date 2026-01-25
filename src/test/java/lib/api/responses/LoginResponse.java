package lib.api.responses;


import io.restassured.response.Response;

public class LoginResponse {
    private final int userId;
    private final String token;
    private final String cookie;

    public LoginResponse(Response response) {
        this.token = response.getHeader("x-csrf-token");
        this.cookie = response.getCookie("auth_sid");
        this.userId = response.jsonPath().getInt("user_id");
    }

    public int getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public String getCookie() {
        return cookie;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId=" + userId +
                ", token='" + token + "'" +
                ", cookie='" + cookie + "'" +
                '}';
    }
}
