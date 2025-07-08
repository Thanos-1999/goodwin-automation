package tests;

import base.BaseTest;
import config.AllureHelper;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {


    private static final String BASE_URL = "https://goodwin.am/en/";
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        Allure.step("Navigate to Goodwin homepage");
        page.navigate(BASE_URL);
        loginPage = new LoginPage(page);
    }

    @Test
    @DisplayName("1. Modal Structure Validation")
    public void testModalStructure() {
        Allure.step("Open the login modal");
        loginPage.openLoginModal();
        Allure.step("Check modal visibility");
        assertTrue(loginPage.isModalVisible());
        Allure.step("Check modal title");
        assertEquals("Log In", loginPage.getModalTitleText());
        Allure.step("Check required UI elements in modal");
        assertTrue(loginPage.isUsernameFieldVisible(), "Username field not visible");
        assertTrue(loginPage.isPasswordFieldVisible(), "Password field not visible");
        assertTrue(loginPage.isSubmitButtonVisible(), "Submit button (Log In) not visible");
        assertTrue(loginPage.isModalcloseiconVisible(), "Close icon not visible");
        assertTrue(loginPage.isSignupButtonVisible(), "Sign Up button not visible");
        assertTrue(loginPage.isForgotPasswordVisible(), "Forgot password not visible");
    }

    @Test
    @DisplayName("2. Empty Form Submission")
    public void testEmptyFormSubmission() {
        try {
            Allure.step("Open the login modal");
            loginPage.openLoginModal();
            Allure.step("Click Log In without entering any credentials");
            loginPage.clickLogin_inactive_state();
            Allure.step("Check validation messages");
            assertTrue(loginPage.isUsernameRequiredVisible(), "Username 'required' error not visible");
            assertTrue(loginPage.isPasswordRequiredVisible(), "Password 'required' error not visible");

            byte[] screenshot = page.screenshot();
            Allure.getLifecycle().addAttachment("Manual Screenshot", "image/png", "png", screenshot);
        } catch (Exception e) {
            AllureHelper.attachScreenshot(page, "Failure Screenshot");
            throw e;
        }

    }

    @Test
    @DisplayName("3. Min Length Validation")
    public void testMinLengthValidation() {
        Allure.step("Open the login modal");
        loginPage.openLoginModal();
        Allure.step("Enter too short credentials");
        loginPage.submitLogin("e", "1");
        Allure.step("Click Log In");
        loginPage.clickLogin_inactive_state();
        Allure.step("Verify min-length validation errors");
        assertTrue(loginPage.isUsernameTooShortVisible(), "Username too short error not visible");
        assertTrue(loginPage.isPasswordTooShortVisible(), "Password too short error not visible");
    }

    @Test
    @DisplayName("4. Password Visibility Toggle")
    public void testPasswordEyeIcon() {
        Allure.step("Open the login modal");
        loginPage.openLoginModal();
        Allure.step("Enter password into password field");
        loginPage.enterPassword("test123");
        Allure.step("Verify password is masked");
        assertEquals("password", loginPage.getPasswordFieldType());
        Allure.step("Toggle password visibility");
        loginPage.togglePasswordVisibility();
        Allure.step("Verify password is visible");
        assertEquals("text", loginPage.getPasswordFieldType());
    }

    @Test
    @DisplayName("5. Invalid Credentials")
    public void testInvalidCredentials() {
        Allure.step("Open the login modal");
        loginPage.openLoginModal();
        Allure.step("Submit invalid username and password");
        loginPage.submitLogin("wronguser", "wrongpass");
        Allure.step("Click Log In");
        loginPage.clickLogin_active_state();
        Allure.step("Verify error modal with message and icons");
        assertTrue(loginPage.isInvalidCredentialErrorVisible(), "Expected error message not visible");
        assertTrue(loginPage.isErrorIconVisible(), "Error icon not visible");
        assertTrue(loginPage.isOkButtonVisible(), "OK button not visible");
    }

    @Test
    @DisplayName("6. Error Modal Handling")
    public void testErrorModalHandling() {
        Allure.step("Open the login modal");
        loginPage.openLoginModal();
        Allure.step("Submit invalid credentials");
        loginPage.submitLogin("wronguser", "wrongpass");
        Allure.step("Click Log In");
        loginPage.clickLogin_active_state();
        Allure.step("Verify error modal appearance");
        assertTrue(loginPage.isErrorIconVisible(), "Error icon not visible");
        assertTrue(loginPage.isOkButtonVisible(), "OK button not visible");
    }

    @Test
    @DisplayName("7. Dismiss Error Modal via OK Button")
    public void testDismissErrorModalOK() {
        Allure.step("Trigger the error modal");
        loginPage.openLoginModal();
        loginPage.submitLogin("wronguser", "wrongpass");
        loginPage.clickLogin_active_state();
        Allure.step("Click OK button in error modal");
        loginPage.clickOkButton();
        Allure.step("Assert modal is dismissed and login remains");
        Assertions.assertFalse(loginPage.isOkButtonVisible(), "Error modal should disappear after clicking OK");
        assertEquals("Log In", loginPage.getModalTitleText());
    }

    @Test
    @DisplayName("8. Dismiss Error Modal via Close Icon (X)")
    public void testDismissErrorModalX() {
        Allure.step("Trigger the error modal");
        loginPage.openLoginModal();
        loginPage.submitLogin("wronguser", "wrongpass");
        loginPage.clickLogin_active_state();
        Allure.step("Click Close (X) icon");
        loginPage.click_X_button();
        Allure.step("Assert error modal is dismissed");
        Assertions.assertFalse(loginPage.isInvalidCredentialErrorVisible(), "Error modal should disappear after clicking the close icon");
        assertEquals("Log In", loginPage.getModalTitleText());
    }
}
