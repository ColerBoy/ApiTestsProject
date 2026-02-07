package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.Assertions;
import lib.api.UserApiClient;


public class UserDeleteSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserDeleteSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("пытается удалить пользователя с ID {int}")
    public void iTryToDeleteUserWithId(int userId) {
        testContext.response = userApiClient.deleteUser(
                userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }

    @Тогда("пользователь удален")
    public void iShouldNotBeAuthorizedAsThisUser() {
        Response responseUserData = userApiClient.getUser(testContext.userId, testContext.loginResponse.getToken(), testContext.loginResponse.getCookie());
        Assertions.assertResponseCodeEquals(responseUserData, 404);
        Assertions.assertResponseTextEquals(responseUserData, "User not found");
    }

    @Когда("удаляет созданного пользователя")
    public void iTryToDeleteTheCreatedUser() {
        testContext.response = userApiClient.deleteUser(
                testContext.userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }
}
