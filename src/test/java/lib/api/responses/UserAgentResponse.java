package lib.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAgentResponse {

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("browser")
    private String browser;

    @JsonProperty("device")
    private String device;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "UserAgentResponse{" +
                "platform='" + platform + "'" +
                ", browser='" + browser + "'" +
                ", device='" + device + "'" +
                '}';
    }
}
