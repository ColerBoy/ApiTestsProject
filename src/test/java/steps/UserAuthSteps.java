package steps;

import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.Assertions;
import lib.api.UserApiClient;


public class UserAuthSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserAuthSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Тогда("пользователь получает {string} равный {string}")
    public void iShouldGetUserId(String key, String value) {
        Response responseCheckAuth = userApiClient.authenticate(
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
        Assertions.assertJsonByName(responseCheckAuth, key, Integer.parseInt(value));
    }

    @Тогда("пользователь пытается авторизоваться с token, но без cookie и получает user_id равный {int}")
    public void iTryToAuthenticateWithTokenButWithoutCookieAndGetUserIdEqualTo(int userId) {
        Response responseForCheck = userApiClient.authenticateWithToken(testContext.loginResponse.getToken());
        Assertions.assertJsonByName(responseForCheck, "user_id", userId);
    }

    @Тогда("пользователь пытается авторизоваться с cookie, но без токена и получает user_id равный {int}")
    public void iTryToAuthenticateWithCookieButWithoutTokenAndGetUserIdEqualTo(int userId) {
        Response responseForCheck = userApiClient.authenticateWithCookie(testContext.loginResponse.getCookie());
        Assertions.assertJsonByName(responseForCheck, "user_id", userId);
    }
}