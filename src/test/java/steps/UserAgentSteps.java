package steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import lib.api.UserAgentApiClient;
import lib.api.responses.UserAgentResponse;

import static org.junit.jupiter.api.Assertions.*;


public class UserAgentSteps {

    private final UserAgentApiClient userAgentApiClient = new UserAgentApiClient();
    private UserAgentResponse response;

    @Когда("пользователь отправляет user agent {string}")
    public void iSendUserAgent(String userAgent) {
        this.response = userAgentApiClient.checkUserAgent(userAgent);
    }

    @Тогда("в ответе пользователь получает платформу {string}, браузер {string} и устройство {string}")
    public void iShouldGetPlatformBrowserAndDevice(String expectedPlatform, String expectedBrowser, String expectedDevice) {
        assertEquals(expectedPlatform, response.getPlatform(), "Platform mismatch");
        assertEquals(expectedBrowser, response.getBrowser(), "Browser mismatch");
        assertEquals(expectedDevice, response.getDevice(), "Device mismatch");
    }

    @Тогда("в ответе пользователь получает платформу {string}, браузер {string} и устройство {string}, поле {string} должно быть неверным")
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
