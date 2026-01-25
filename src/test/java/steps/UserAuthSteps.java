package steps;

import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.Assertions;
import lib.api.UserApiClient;

@Epic("Authorisation cases")
@Feature("Authorization")
@Owner(value = "Максим QA")
@Issue(value = "TEST-123")
public class UserAuthSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserAuthSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Тогда("я должен получить {string} равный {string}")
    @Story("Positive")
    @Description("Этот тест успешно аутентифицирует пользователя с валидным email и паролем.")
    public void iShouldGetUserId(String key, String value) {
        Response responseCheckAuth = userApiClient.authenticate(
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
        Assertions.assertJsonByName(responseCheckAuth, key, Integer.parseInt(value));
    }

    @Тогда("я пытаюсь аутентифицироваться с токеном, но без cookie и получаю user_id равный {int}")
    @Story("Negative")
    @Description("Этот тест проверяет, что аутентификация не проходит без cookie.")
    public void iTryToAuthenticateWithTokenButWithoutCookieAndGetUserIdEqualTo(int userId) {
        Response responseForCheck = userApiClient.authenticateWithToken(testContext.loginResponse.getToken());
        Assertions.assertJsonByName(responseForCheck, "user_id", userId);
    }

    @Тогда("я пытаюсь аутентифицироваться с cookie, но без токена и получаю user_id равный {int}")
    @Story("Negative")
    @Description("Этот тест проверяет, что аутентификация не проходит без токена.")
    public void iTryToAuthenticateWithCookieButWithoutTokenAndGetUserIdEqualTo(int userId) {
        Response responseForCheck = userApiClient.authenticateWithCookie(testContext.loginResponse.getCookie());
        Assertions.assertJsonByName(responseForCheck, "user_id", userId);
    }
}