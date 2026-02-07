package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.api.UserApiClient;
import lib.api.requests.CreateUserRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserEditSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserEditSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("меняет имя на {string}")
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

    @Когда("пытается изменить имя на {string} без аутентификации")
    public void iTryToChangeTheNameToWithoutAuthentication(String newName) {
        CreateUserRequest editData = new CreateUserRequest.Builder().withFirstName(newName).build();
        testContext.response = userApiClient.editUser(testContext.userId, editData);
    }

    @Когда("пытается изменить имя другого пользователя на {string}")
    public void iTryToChangeTheNameOfAnotherUserTo(String newName) {
        CreateUserRequest editData = new CreateUserRequest.Builder().withFirstName(newName).build();
        testContext.response = userApiClient.editUser(
                testContext.userId + 1, // Attempt to edit a different user
                testContext.loginResponse.getToken(),
                testContext.loginResponse.getCookie(),
                editData
        );
    }

    @Когда("пытается изменить email на {string}")
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
