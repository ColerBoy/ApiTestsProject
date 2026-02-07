package lib.api;

import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.api.requests.CreateUserRequest;
import lib.api.responses.LoginResponse;
import lib.config.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserApiClient {
    private static final String LOGIN_ENDPOINT = "user/login";
    private static final String AUTH_ENDPOINT = "user/auth";
    private static final String USER_ENDPOINT = "user/";
    private static final String CSRF_TOKEN_HEADER = "x-csrf-token";
    private static final String AUTH_COOKIE_NAME = "auth_sid";

    private RequestSpecification getReqSpec() {
        return given()
                .baseUri(Configuration.getBaseUrl());
    }

    public LoginResponse login(String email, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        Response response = getReqSpec()
                .body(body)
                .post(LOGIN_ENDPOINT)
                .andReturn();

        return new LoginResponse(response);
    }

    public Response authenticate(String token, String cookie) {
        return getReqSpec()
                .header(new Header(CSRF_TOKEN_HEADER, token))
                .cookie(AUTH_COOKIE_NAME, cookie)
                .get(AUTH_ENDPOINT)
                .andReturn();
    }

    public Response authenticateWithCookie(String cookie) {
        return getReqSpec()
                .cookie(AUTH_COOKIE_NAME, cookie)
                .get(AUTH_ENDPOINT)
                .andReturn();
    }

    public Response authenticateWithToken(String token) {
        return getReqSpec()
                .header(new Header(CSRF_TOKEN_HEADER, token))
                .get(AUTH_ENDPOINT)
                .andReturn();
    }

    public Response createUser(CreateUserRequest userData) {
        return getReqSpec()
                .body(userData)
                .post(USER_ENDPOINT)
                .andReturn();
    }

    public Response getUser(int userId, String token, String cookie) {
        return getReqSpec()
                .header(new Header(CSRF_TOKEN_HEADER, token))
                .cookie(AUTH_COOKIE_NAME, cookie)
                .get(USER_ENDPOINT + userId)
                .andReturn();
    }

    public Response getUser(int userId) {
        return getReqSpec()
                .get(USER_ENDPOINT + userId)
                .andReturn();
    }

    public Response editUser(int userId, String token, String cookie, CreateUserRequest userData) {
        return getReqSpec()
                .header(new Header(CSRF_TOKEN_HEADER, token))
                .cookie(AUTH_COOKIE_NAME, cookie)
                .body(userData)
                .put(USER_ENDPOINT + userId)
                .andReturn();
    }

    public Response editUser(int userId, CreateUserRequest userData) {
        return getReqSpec()
                .body(userData)
                .put(USER_ENDPOINT + userId)
                .andReturn();
    }

    public Response deleteUser(int userId, String token, String cookie) {
        return getReqSpec()
                .header(new Header(CSRF_TOKEN_HEADER, token))
                .cookie(AUTH_COOKIE_NAME, cookie)
                .delete(USER_ENDPOINT + userId)
                .andReturn();
    }
}
