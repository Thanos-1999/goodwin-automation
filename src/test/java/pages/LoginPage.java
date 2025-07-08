package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginPage {

    private final Page page;
    private final Locator loginTriggerButton;
    private final Locator modal;
    private final Locator closeIcon;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator signupButton;
    private final Locator forgotPassword;
    private final Locator modalTitle;
    private final Locator eyeIcon;
    private final Locator errorIcon;
    private final Locator okButton;
    private final Locator modal_close;



    public LoginPage(Page page) {
        this.page = page;

        this.modal = page.locator("div[class*='modal-dialog']");
        this.modal_close = page.locator(".icon-close");
        this.modalTitle = page.locator(".modal__title.modal__title--xs");
        this.loginTriggerButton = page.locator("button.button--sign-in");
        this.usernameInput = page.locator("input[name='username']");
        this.passwordInput = page.locator("input[name='password']");
        this.loginButton = page.locator("button:has-text('Log In'):not([disabled])").first();
        this.signupButton = modal.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Sign Up"));
        this.forgotPassword = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Forgot your password?"));
        this.eyeIcon = page.locator("i[class*='icon-eye']");
        this.closeIcon = page.locator("(//i[@class='icon-close'])[2]");
        this.errorIcon = page.locator(".modal__icon.icon-form-alert.modal__icon--error");
        this.okButton = page.locator("button:has-text('OK')");
    }

    public void openLoginModal() {
        loginTriggerButton.click();
    }

    public void enterUsername(String username) {
        usernameInput.fill(username);
    }

    public void enterPassword(String password) {
        passwordInput.fill(password);
    }

    public void clickLogin_active_state() {
        Locator loginButton = page.locator("button.button--content.finish-button.button--primary.button--flex.button--visible.button--large:has-text('Log In')");
        loginButton.click(new Locator.ClickOptions().setForce(true));
        page.waitForTimeout(3000);
    }

    public void clickLogin_inactive_state() {
        loginButton.click(new Locator.ClickOptions().setForce(true));
        page.waitForTimeout(3000);
    }

    public void submitLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);

    }

    public boolean isModalVisible() {
        return modal.isVisible();
    }

    public String getModalTitleText() {
        return modalTitle.innerText().trim();
    }

    public boolean isCloseIconVisible() {
        return closeIcon.isVisible();
    }

    public boolean isModalcloseiconVisible() {
        return modal_close.isVisible();
    }

    public boolean isUsernameFieldVisible() {
        return usernameInput.isVisible();
    }

    public boolean isPasswordFieldVisible() {
        return passwordInput.isVisible();
    }

    public boolean isSubmitButtonVisible() {
        return loginButton.isVisible();
    }

    public boolean isSignupButtonVisible() {
        return signupButton.isVisible();
    }

    public boolean isForgotPasswordVisible() {
        return forgotPassword.isVisible();
    }

    public boolean isUsernameRequiredVisible() {
        return page.locator("text=This field is required").first().isVisible();
    }

    public boolean isPasswordRequiredVisible() {
        return page.locator("text=This field is required").nth(1).isVisible();
    }

    public boolean isUsernameTooShortVisible() {
        return page.locator("text=This field must contain at least 3 characters").isVisible();
    }

    public boolean isPasswordTooShortVisible() {
        return page.locator("text=Password should have length of at least 6 characters").isVisible();
    }

    public void togglePasswordVisibility() {
        eyeIcon.click();
    }

    public boolean isInvalidCredentialErrorVisible() {
        Locator errorModalText = page.locator("p.modal__subtitle:has-text('Username/password combination is wrong')");
        return errorModalText.isVisible();
    }

    public String getPasswordFieldType() {
        return passwordInput.getAttribute("type");
    }

    public boolean isErrorIconVisible() {
        return errorIcon.isVisible();
    }

    public boolean isOkButtonVisible() {
        return okButton.isVisible() && okButton.isEnabled();
    }

    public void click_X_button() {
        closeIcon.click();
    }

    public void clickOkButton() {
        okButton.click();
    }
}
