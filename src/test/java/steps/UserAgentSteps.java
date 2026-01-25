package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lib.api.UserAgentApiClient;
import lib.api.responses.UserAgentResponse;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Agent")
@Feature("User Agent Verification")
public class UserAgentSteps {

    private final UserAgentApiClient userAgentApiClient = new UserAgentApiClient();
    private UserAgentResponse response;

    @Когда("я отправляю user agent {string}")
    public void iSendUserAgent(String userAgent) {
        this.response = userAgentApiClient.checkUserAgent(userAgent);
    }

    @Тогда("я должен получить платформу {string}, браузер {string} и устройство {string}")
    @Story("Positive")
    @Description("Этот тест проверяет, что различные валидные user agents парсятся корректно.")
    public void iShouldGetPlatformBrowserAndDevice(String expectedPlatform, String expectedBrowser, String expectedDevice) {
        assertEquals(expectedPlatform, response.getPlatform(), "Platform mismatch");
        assertEquals(expectedBrowser, response.getBrowser(), "Browser mismatch");
        assertEquals(expectedDevice, response.getDevice(), "Device mismatch");
    }

    @Тогда("я должен получить платформу {string}, браузер {string} и устройство {string}, но поле {string} должно быть неверным")
    @Story("Negative")
    @Description("Этот тест проверяет, что определенные поля в парсинге user agent могут быть некорректными для некоторых user agents.")
    public void iShouldGetPlatformBrowserAndDeviceButFieldShouldBeIncorrect(String expectedPlatform, String expectedBrowser, String expectedDevice, String brokenField) {
        assertEquals(expectedPlatform, response.getPlatform(), "Platform mismatch");

        switch (brokenField) {
            case "browser":
                assertNotEquals(expectedBrowser, response.getBrowser(), "Browser should NOT match");
                assertEquals(expectedDevice, response.getDevice(), "Device mismatch");
                break;
            case "device":
                assertEquals(expectedBrowser, response.getBrowser(), "Browser mismatch");
                assertNotEquals(expectedDevice, response.getDevice(), "Device should NOT match");
                break;
            default:
                fail("Unknown broken field: " + brokenField);
        }
    }
}
