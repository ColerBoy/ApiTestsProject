package lib;

import lib.api.requests.CreateUserRequest;

import java.text.SimpleDateFormat;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";
    }

    public static CreateUserRequest.Builder getRegistrationData() {
        return new CreateUserRequest.Builder()
                .withEmail(getRandomEmail())
                .withPassword("123")
                .withUsername("learnqa")
                .withFirstName("learnqa")
                .withLastName("learnqa");
    }
}
