package lib.api;

import lib.api.responses.UserAgentResponse;

import static io.restassured.RestAssured.given;

public class UserAgentApiClient {

    private static final String USER_AGENT_CHECK_ENDPOINT = "https://playground.learnqa.ru/ajax/api/user_agent_check";

    public UserAgentResponse checkUserAgent(String userAgent) {
        return given()
                .header("User-Agent", userAgent)
                .get(USER_AGENT_CHECK_ENDPOINT)
                .as(UserAgentResponse.class);
    }
}
