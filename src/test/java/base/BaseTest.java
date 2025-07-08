package base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ScreenshotType;
import config.ConfigReader;
import factory.BrowserFactory;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected static Page page;

    @BeforeEach
    public void setup() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        System.out.println("Screen size: " + width + "x" + height);
        playwright = Playwright.create();
        browser = BrowserFactory.getBrowser(playwright);
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920,1080));
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }}









