package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import lib.Assertions;
import lib.DataGenerator;
import lib.api.UserApiClient;
import lib.api.requests.CreateUserRequest;

public class CommonSteps {
    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("я создаю и логинюсь как новый пользователь")
    public void iCreateAndLoginAsNewUser() {
        CreateUserRequest createUserData = DataGenerator.getRegistrationData().build();
        Response createResponse = userApiClient.createUser(createUserData);
        testContext.userId = createResponse.jsonPath().getInt("id");
        testContext.loginResponse = userApiClient.login(createUserData.getEmail(), createUserData.getPassword());
    }

    @Тогда("я должен получить код ответа {int}")
    public void iShouldGetStatusCode(int statusCode) {
        Assertions.assertResponseCodeEquals(testContext.response, statusCode);
    }

    @Тогда("тело ответа должно быть {string}")
    public void responseBodyShouldBe(String responseBody) {
        Assertions.assertResponseTextEquals(testContext.response, responseBody);
    }

    @Когда("я логинюсь как {string} с паролем {string}")
    public void iLoginAsWithPassword(String email, String password) {
        testContext.loginResponse = userApiClient.login(email, password);
    }

    @Когда("я создаю нового пользователя")
    public void iCreateNewUser() {
        CreateUserRequest createUserData = DataGenerator.getRegistrationData().build();
        Response createResponse = userApiClient.createUser(createUserData);
        testContext.userId = createResponse.jsonPath().getInt("id");
    }

    @Тогда("сообщение об ошибке должно быть {string}")
    public void errorMessageShouldBe(String errorMessage) {
        Assertions.assertErrorResponseTextEquals(testContext.response, errorMessage);
    }
}
