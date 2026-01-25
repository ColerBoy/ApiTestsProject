package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.api.UserApiClient;
import lib.api.requests.CreateUserRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("User Management")
@Feature("Edit User")
@Owner(value = "Максим QA")
@Issue(value = "TEST-123")
public class UserEditSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserEditSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("я меняю имя на {string}")
    @Story("Positive")
    @Description("Этот тест проверяет, что пользователь может успешно отредактировать свое имя.")
    public void iChangeTheNameTo(String newName) {
        CreateUserRequest editData = new CreateUserRequest.Builder().withFirstName(newName).build();
        testContext.response = userApiClient.editUser(
                testContext.userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie(),
                editData
        );
    }

    @Тогда("имя пользователя должно быть {string}")
    public void theUsernameShouldBe(String newName) {
        Response userResponse = userApiClient.getUser(
                testContext.userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie()
        );
        assertEquals(newName, userResponse.jsonPath().getString("firstName"), "The first name was not updated.");
    }

    @Когда("я пытаюсь изменить имя на {string} без аутентификации")
    @Story("Negative")
    @Description("Этот тест проверяет, что данные пользователя не могут быть отредактированы без аутентификации.")
    public void iTryToChangeTheNameToWithoutAuthentication(String newName) {
        CreateUserRequest editData = new CreateUserRequest.Builder().withFirstName(newName).build();
        testContext.response = userApiClient.editUser(testContext.userId, editData);
    }

    @Когда("я пытаюсь изменить имя другого пользователя на {string}")
    @Story("Negative")
    @Description("Этот тест проверяет, что пользователь не может редактировать данные другого пользователя.")
    public void iTryToChangeTheNameOfAnotherUserTo(String newName) {
        CreateUserRequest editData = new CreateUserRequest.Builder().withFirstName(newName).build();
        testContext.response = userApiClient.editUser(
                testContext.userId + 1, // Attempt to edit a different user
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie(),
                editData
        );
    }

    @Когда("я пытаюсь изменить email на {string}")
    @Story("Negative")
    @Description("Этот тест проверяет, что пользователь не может изменить свой email на невалидный.")
    public void iTryToChangeTheEmailTo(String newEmail) {
        CreateUserRequest editData = new CreateUserRequest.Builder().withEmail(newEmail).build();
        testContext.response = userApiClient.editUser(
                testContext.userId,
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie(),
                editData
        );
    }
}
