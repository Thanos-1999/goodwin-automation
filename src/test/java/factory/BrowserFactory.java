package factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import config.ConfigReader;

import java.util.Collections;

public class BrowserFactory {
    public static Browser getBrowser(Playwright playwright) {
        String browserName = ConfigReader.get("browser").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));

        System.out.println("Launching browser: " + browserName + ", headless: " + headless);

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);



        switch (browserName) {
            case "firefox":
                return playwright.firefox().launch(options);
            case "webkit":
                return playwright.webkit().launch(options);
            case "chrome":
            case "chromium":
                options.setArgs(Collections.singletonList("--start-fullscreen"));
                return playwright.chromium().launch(options);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }
}