package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.Assertions;
import lib.api.UserApiClient;

@Epic("User Management")
@Feature("Delete User")
@Owner(value = "Максим QA")
@Issue(value = "TEST-123")
public class UserDeleteSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserDeleteSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("я пытаюсь удалить пользователя с ID {int}")
    @Story("Negative")
    @Description("Этот тест проверяет, что защищенный пользователь (ID 2) не может быть удален.")
    public void iTryToDeleteUserWithId(int userId) {
        testContext.response = userApiClient.deleteUser(
                userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }

    @Когда("я удаляю этого пользователя")
    @Story("Positive")
    @Description("Этот тест проверяет, что пользователь может успешно удалить свой аккаунт.")
    public void iDeleteThisUser() {
        userApiClient.deleteUser(
                testContext.userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }

    @Тогда("я не должен быть авторизован под этим пользователем")
    public void iShouldNotBeAuthorizedAsThisUser() {
        Response responseUserData = userApiClient.getUser(testContext.userId, testContext.loginResponse.getToken(), testContext.loginResponse.getCookie());
        Assertions.assertResponseCodeEquals(responseUserData, 404);
        Assertions.assertResponseTextEquals(responseUserData, "User not found");
    }

    @Когда("я пытаюсь удалить созданного пользователя")
    @Story("Negative")
    @Description("Этот тест проверяет, что пользователь не может удалить аккаунт другого пользователя.")
    public void iTryToDeleteTheCreatedUser() {
        testContext.response = userApiClient.deleteUser(
                testContext.userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
    }
}
