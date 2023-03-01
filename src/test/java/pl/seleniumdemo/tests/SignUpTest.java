package pl.seleniumdemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.LoggedUserPage;
import pl.seleniumdemo.pages.SignUpPage;

import java.util.List;

public class SignUpTest extends BaseTest {

    @Test
    public void signUp() {
        final String lastName = "Testowy";
        int randomNumber = (int) (Math.random() * 1000);
        final String email = "tester" + randomNumber + "@xd.pl";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Bartosz");
        signUpPage.setLastName(lastName);
        signUpPage.setPhone("456456456");
        signUpPage.setEmail(email);
        signUpPage.setPassword("123123");
        signUpPage.setConfirmPassword("123123");
        signUpPage.clickSignUpBtn();

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Hi, Bartosz Testowy");
    }

    @Test
    public void signUpWithoutProvidingAnyValues() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.clickSignUpBtn();

        List<String> errorsList = signUpPage.getErrors();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errorsList.contains("The Email field is required."));
        softAssert.assertTrue(errorsList.contains("The Password field is required."));
        softAssert.assertTrue(errorsList.contains("The Password field is required."));
        softAssert.assertTrue(errorsList.contains("The First name field is required."));
        softAssert.assertTrue(errorsList.contains("The Last Name field is required."));
        softAssert.assertAll();
    }

    @Test
    public void signUpWithInvalidEmail() {
        final String lastName = "Testowy";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Bartosz");
        signUpPage.setLastName(lastName);
        signUpPage.setPhone("456456456");
        signUpPage.setEmail("email");
        signUpPage.setPassword("123123");
        signUpPage.setConfirmPassword("123123");
        signUpPage.clickSignUpBtn();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));
    }
}
