package steps;

import io.restassured.response.Response;
import lib.api.responses.LoginResponse;

public class TestContext {
    public LoginResponse loginResponse;
    public int userId;
    public Response response;
}
