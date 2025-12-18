package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Epic("UserAgent cases")
@Feature("User agent check")
public class UserAgentTest extends BaseTestCase {
    private final static String URL = "https://playground.learnqa.ru/ajax/api/user_agent_check";
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @ParameterizedTest
    @Story("Позитивный тест")
    @Description("This test cheks user agent")
    @DisplayName("Test positive user agent check")
    @CsvSource({
            // userAgent###expectedPlatform###expectedBrowser###expectedDevice
            "'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30', Mobile, No, Android",
            "'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0', Web, Chrome, No"
    })
    public void userAgentPositiveTest(String userAgent, String expectedPlatform, String expectedBrowser, String expectedDevice) {
        Response response = apiCoreRequests.makeGetRequestWithHeader(URL, userAgent);

        String actualPlatform = response.jsonPath().getString("platform");
        String actualBrowser = response.jsonPath().getString("browser");
        String actualDevice = response.jsonPath().getString("device");

        assertEquals(expectedPlatform, actualPlatform, "Platform mismatch");
        assertEquals(expectedBrowser, actualBrowser, "Browser mismatch");
        assertEquals(expectedDevice, actualDevice, "Device mismatch");
    }

    @ParameterizedTest
    @Story("Негативный тест")
    @Description("This test cheks user agent")
    @DisplayName("Test negative user agent check")
    @CsvSource({
            // Формат: userAgent###expectedPlatform###expectedBrowser###expectedDevice###brokenField
            "'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1', Mobile, Chrome, iOS, browser",
            "'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1', Mobile, No, iPhone, device"
    })
    public void userAgentNegativeTest(String userAgent, String expectedPlatform, String expectedBrowser, String expectedDevice, String brokenField) {
        Response response = apiCoreRequests.makeGetRequestWithHeader(URL, userAgent);

        String actualPlatform = response.jsonPath().getString("platform");
        String actualBrowser = response.jsonPath().getString("browser");
        String actualDevice = response.jsonPath().getString("device");


        assertEquals(expectedPlatform, actualPlatform, "Platform mismatch");


        switch (brokenField) {
            case "browser" -> {
                assertNotEquals(expectedBrowser, actualBrowser, "Browser should NOT match");
                assertEquals(expectedDevice, actualDevice, "Device mismatch");
            }
            case "device" -> {
                assertEquals(expectedBrowser, actualBrowser, "Browser mismatch");
                assertNotEquals(expectedDevice, actualDevice, "Device should NOT match");
            }
            default -> fail("Unknown broken field: " + brokenField);
        }
    }
}