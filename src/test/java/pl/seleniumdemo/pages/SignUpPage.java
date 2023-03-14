package pl.seleniumdemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.seleniumdemo.model.User;
import utils.SeleniumHelper;

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

    public void setFirstName(final String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    public void setLastName(final String lastName) {
        lastNameInput.sendKeys(lastName);
    }

    public void setEmail(final String email) {
        emailInput.sendKeys(email);
    }

    public void setPhone(final String phone) {
        phoneInput.sendKeys(phone);
    }

    public void setPassword(final String password) {
        passwordInput.sendKeys(password);
    }

    public void setConfirmPassword(final String confirmPassword) {
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void clickSignUpBtn() {
        signUpBtn.click();
    }

    public List<String> getErrors() {
        SeleniumHelper.waitForNotEmptyList(driver, By.xpath("//div[@class='alert alert-danger']//p"));
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
