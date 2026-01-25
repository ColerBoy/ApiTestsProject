package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import lib.DataGenerator;
import lib.api.UserApiClient;
import lib.api.requests.CreateUserRequest;

import static org.hamcrest.Matchers.hasKey;

@Epic("User Management")
@Feature("User Registration")
@Owner(value = "Максим QA")
@Issue(value = "TEST-123")
public class UserRegisterSteps {

    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserRegisterSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("я создаю нового пользователя с валидными данными")
    @Story("Positive")
    @Description("Этот тест проверяет, что новый пользователь может быть успешно создан.")
    public void iCreateNewUserWithValidData() {
        CreateUserRequest userData = DataGenerator.getRegistrationData().build();
        testContext.response = userApiClient.createUser(userData);
    }

    @Тогда("в ответе должен быть {string}")
    public void responseShouldHave(String field) {
        testContext.response.then().assertThat().body("$", hasKey(field));
    }

    @Когда("я создаю нового пользователя с email {string}")
    @Story("Negative")
    @Description("Этот тест проверяет, что пользователь не может быть создан с уже существующим email.")
    public void iCreateNewUserWithEmail(String email) {
        CreateUserRequest userData = DataGenerator.getRegistrationData().withEmail(email).build();
        testContext.response = userApiClient.createUser(userData);
    }

    @Когда("я создаю нового пользователя без поля {string}")
    @Story("Negative")
    @Description("Этот тест проверяет, что пользователь не может быть создан без одного из обязательных полей.")
    public void iCreateNewUserWithoutField(String missingField) {
        CreateUserRequest.Builder builder = DataGenerator.getRegistrationData();

        switch (missingField) {
            case "email":
                builder.withEmail(null);
                break;
            case "password":
                builder.withPassword(null);
                break;
            case "username":
                builder.withUsername(null);
                break;
            case "firstName":
                builder.withFirstName(null);
                break;
            case "lastName":
                builder.withLastName(null);
                break;
        }

        testContext.response = userApiClient.createUser(builder.build());
    }

    @Когда("я создаю нового пользователя с именем {string}")
    @Story("Negative")
    @Description("Этот тест проверяет, что пользователь не может быть создан с именем, которое слишком короткое или слишком длинное.")
    public void iCreateNewUserWithFirstName(String firstName) {
        CreateUserRequest userData = DataGenerator.getRegistrationData().withFirstName(firstName).build();
        testContext.response = userApiClient.createUser(userData);
    }
}
