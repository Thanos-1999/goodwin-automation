package config;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.function.Supplier;

public class AllureHelper {
    public static void attachScreenshot(Page page, String name) {
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
    }
}