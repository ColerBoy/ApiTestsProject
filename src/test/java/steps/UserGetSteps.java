package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import lib.api.UserApiClient;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Get User Data")
@Owner(value = "Максим QA")
@Issue(value = "TEST-123")
public class UserGetSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserGetSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("я запрашиваю данные пользователя с ID {int} без аутентификации")
    @Story("Negative")
    @Description("Этот тест проверяет, что конфиденциальные данные пользователя не доступны без аутентификации.")
    public void iRequestUserDataWithIdWithoutAuth(int userId) {
        testContext.response = userApiClient.getUser(userId);
    }

    @Тогда("я должен получить только имя пользователя")
    public void iShouldGetOnlyUsername() {
        assertNull(testContext.response.jsonPath().getString("firstName"), "First name should not be available without authentication");
        assertNull(testContext.response.jsonPath().getString("lastName"), "Last name should not be available without authentication");
        assertNull(testContext.response.jsonPath().getString("email"), "Email should not be available without authentication");
        assertNotNull(testContext.response.jsonPath().getString("username"), "Username should be available without authentication");
    }

    @Когда("я запрашиваю свои данные")
    @Story("Positive")
    @Description("Этот тест проверяет, что пользователь может получить свои данные после аутентификации.")
    public void iRequestMyData() {
        testContext.response = userApiClient.getUser(
                testContext.loginResponse.getUserId(),
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }

    @Тогда("я должен получить все свои данные")
    public void iShouldGetAllMyData() {
        assertNotNull(testContext.response.jsonPath().getString("username"), "Username should be available");
        assertNotNull(testContext.response.jsonPath().getString("firstName"), "First name should be available");
        assertNotNull(testContext.response.jsonPath().getString("lastName"), "Last name should be available");
        assertEquals("vinkotov@example.com", testContext.response.jsonPath().getString("email"), "Email does not match");
    }

    @Когда("я запрашиваю данные пользователя с ID {int}")
    @Story("Negative")
    @Description("Этот тест проверяет, что пользователь не может видеть конфиденциальные данные другого пользователя.")
    public void iRequestUserDataWithId(int userId) {
        testContext.response = userApiClient.getUser(
                userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }
}