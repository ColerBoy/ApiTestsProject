package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.*;
import lib.DataGenerator;
import lib.api.UserApiClient;
import lib.api.requests.CreateUserRequest;

import static org.hamcrest.Matchers.hasKey;


public class UserRegisterSteps {

    private final TestContext testContext;
    private final UserApiClient userApiClient = new UserApiClient();

    public UserRegisterSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Когда("регистрируется новый пользователь")
    public void iCreateNewUserWithValidData() {
        CreateUserRequest userData = DataGenerator.getRegistrationData().build();
        testContext.response = userApiClient.createUser(userData);
    }

    @Тогда("в ответе должен быть {string}")
    public void responseShouldHave(String field) {
        testContext.response.then().assertThat().body("$", hasKey(field));
    }

    @Когда("пользователь регистрируется с email {string}")
    public void iCreateNewUserWithEmail(String email) {
        CreateUserRequest userData = DataGenerator.getRegistrationData().withEmail(email).build();
        testContext.response = userApiClient.createUser(userData);
    }

    @Когда("регистрируется пользователь без поля {string}")
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

    @Когда("регистрируется пользователь с именем {string}")
    public void iCreateNewUserWithFirstName(String firstName) {
        CreateUserRequest userData = DataGenerator.getRegistrationData().withFirstName(firstName).build();
        testContext.response = userApiClient.createUser(userData);
    }
}
