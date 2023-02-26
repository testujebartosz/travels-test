package pl.seleniumdemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.seleniumdemo.model.User;
import pl.seleniumdemo.tests.BaseTest;

import java.util.List;

public class SignUpPage {

    @FindBy(name = "firstname")
    private WebElement firstNameInput;

    @FindBy(name = "lastname")
    private WebElement lastNameInput;

    @FindBy(name = "phone")
    private WebElement phoneInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(name = "confirmpassword")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[text()=' Sign Up']")
    private WebElement signUpBtn;

    @FindBy(xpath = "//div[@class='alert alert-danger']//p")
    private List<WebElement> errors;

    private final WebDriver driver;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public SignUpPage setFirstName(final String firstName) {
        firstNameInput.sendKeys(firstName);
        return this;
    }

    public SignUpPage setLastName(final String lastName) {
        lastNameInput.sendKeys(lastName);
        return this;
    }

    public SignUpPage setEmail(final String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public SignUpPage setPhone(final String phone) {
        phoneInput.sendKeys(phone);
        return this;
    }

    public SignUpPage setPassword(final String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public SignUpPage setConfirmPassword(final String confirmPassword) {
        confirmPasswordInput.sendKeys(confirmPassword);
        return this;
    }

    public LoggedUserPage clickSignUpBtn() {
        signUpBtn.click();
        return new LoggedUserPage(driver);
    }

    public List<String> getErrors() {
        return errors
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public void fillSignUpForm(final String firstName, final String lastName, final String phone,
                               final String email, final String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setEmail(email);
        setPassword(password);
        setConfirmPassword(password);
        clickSignUpBtn();
    }

    public void fillSignUpFormWithObject(User user) {
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPhone(user.getPhone());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setConfirmPassword(user.getPassword());
        clickSignUpBtn();
    }
}
