package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import lib.api.UserApiClient;

import static org.junit.jupiter.api.Assertions.*;


public class UserGetSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserGetSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("запрашиваются данные пользователя с ID {int} без аутентификации")
    public void iRequestUserDataWithIdWithoutAuth(int userId) {
        testContext.response = userApiClient.getUser(userId);
    }

    @Тогда("доступно только имя пользователя")
    public void iShouldGetOnlyUsername() {
        assertNull(testContext.response.jsonPath().getString("firstName"), "First name should not be available without authentication");
        assertNull(testContext.response.jsonPath().getString("lastName"), "Last name should not be available without authentication");
        assertNull(testContext.response.jsonPath().getString("email"), "Email should not be available without authentication");
        assertNotNull(testContext.response.jsonPath().getString("username"), "Username should be available without authentication");
    }

    @Когда("запрашивает свои данные")
    public void iRequestMyData() {
        testContext.response = userApiClient.getUser(
                testContext.loginResponse.getUserId(),
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }

    @Тогда("пользователь получает все свои данные")
    public void iShouldGetAllMyData() {
        assertNotNull(testContext.response.jsonPath().getString("username"), "Username should be available");
        assertNotNull(testContext.response.jsonPath().getString("firstName"), "First name should be available");
        assertNotNull(testContext.response.jsonPath().getString("lastName"), "Last name should be available");
        assertEquals("vinkotov@example.com", testContext.response.jsonPath().getString("email"), "Email does not match");
    }

    @Когда("запрашивает данные пользователя с ID {int}")
    public void iRequestUserDataWithId(int userId) {
        testContext.response = userApiClient.getUser(
                userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }
}