package lib.api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import lib.api.responses.UserAgentResponse;

import static io.restassured.RestAssured.given;

public class UserAgentApiClient {

    private static final String USER_AGENT_CHECK_ENDPOINT = "https://playground.learnqa.ru/ajax/api/user_agent_check";

    @Step("Check user agent")
    public UserAgentResponse checkUserAgent(String userAgent) {
        return given()
                .filter(new AllureRestAssured())
                .header("User-Agent", userAgent)
                .get(USER_AGENT_CHECK_ENDPOINT)
                .as(UserAgentResponse.class);
    }
}
