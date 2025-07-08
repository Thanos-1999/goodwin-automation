package config;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;
import java.util.function.Supplier;

public class TestResultListener implements TestWatcher {
    private final Supplier<Page> pageSupplier;

    public TestResultListener(Supplier<Page> pageSupplier) {
        this.pageSupplier = pageSupplier;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try {
            byte[] screenshot = pageSupplier.get().screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.getLifecycle().addAttachment("Failure Screenshot", "image/png", "png", screenshot);
        } catch (Exception e) {
            System.err.println("Screenshot capture failed: " + e.getMessage());
        }
    }
}
