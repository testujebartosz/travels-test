package pl.seleniumdemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.seleniumdemo.tests.BaseTest;

import java.time.Duration;
import java.util.List;

public class SignUpTest extends BaseTest {

    @Test
    public void signUp() {
        final String lastName = "Testowy";
        int randomNumber = (int) (Math.random() * 1000);
        final String email = "tester" + randomNumber + "@xd.pl";
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        driver.findElement(By.name("firstname")).sendKeys("Bartosz");
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys("555666777");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("1234567890");
        driver.findElement(By.name("confirmpassword")).sendKeys("1234567890");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//h3[@class='RTL']"))));

        WebElement heading = driver.findElement(By.xpath("//h3[@class='RTL']"));
        Assert.assertTrue(heading.getText().contains(lastName));
        Assert.assertEquals(heading.getText(), "Hi, Bartosz Testowy");
    }

    @Test
    public void signUpWithoutProvidingAnyValues() {
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        List<String> errorsList = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p"))
                .stream()
                .map(WebElement::getText)
                .toList();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(errorsList.contains("The Email field is required."));
        softAssert.assertTrue(errorsList.contains("The Password field is required."));
        softAssert.assertTrue(errorsList.contains("The Password field is required."));
        softAssert.assertTrue(errorsList.contains("The First name field is required."));
        softAssert.assertTrue(errorsList.contains("The Last Name field is required."));

        softAssert.assertAll();

        WebElement emailErrorMsg = driver.findElement(By.xpath("//p[text()='The Email field is required.']"));
        WebElement passwordErrorMsg = driver.findElement(By.xpath("//p[text()='The Password field is required.']"));
        WebElement firstNameErrorMsg = driver.findElement(By.xpath("//p[text()='The First name field is required.']"));
        WebElement lastNameErrorMsg = driver.findElement(By.xpath("//p[text()='The Last Name field is required.']"));

        Assert.assertEquals(emailErrorMsg.getText(), "The Email field is required.");
        Assert.assertEquals(passwordErrorMsg.getText(), "The Password field is required.");
        Assert.assertEquals(firstNameErrorMsg.getText(), "The First name field is required.");
        Assert.assertEquals(lastNameErrorMsg.getText(), "The Last Name field is required.");
    }

    @Test
    public void signUpWithInvalidEmail() {
        final String lastName = "Testowy";
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();

        driver.findElement(By.name("firstname")).sendKeys("Bartosz");
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys("555666777");
        driver.findElement(By.name("email")).sendKeys("email");
        driver.findElement(By.name("password")).sendKeys("1234567890");
        driver.findElement(By.name("confirmpassword")).sendKeys("1234567890");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        WebElement emailInvalidMsg = driver.findElement(By.xpath("//p[text()='The Email field must contain a valid email address.']"));
        Assert.assertEquals(emailInvalidMsg.getText(), "The Email field must contain a valid email address.");
    }
}
