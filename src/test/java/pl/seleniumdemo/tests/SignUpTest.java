package pl.seleniumdemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.seleniumdemo.model.User;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.LoggedUserPage;
import pl.seleniumdemo.pages.SignUpPage;

import java.util.List;

public class SignUpTest extends BaseTest {

    @Test
    public void signUpTest() {
        final String lastName = "Testowy";

        //fluent way
        LoggedUserPage loggedUserPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstName("Bartosz")
                .setPhone("545455545")
                .setLastName(lastName)
                .setEmail(setEmailName())
                .setPassword("xxx123")
                .setConfirmPassword("xxx123")
                .clickSignUpBtn();

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Hi, Bartosz Testowy");
    }

    @Test
    public void signUpTest2() {
        final String lastName = "Testowy";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.fillSignUpForm("Bartosz", lastName, "123123123", setEmailName(), "text123");

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        loggedUserPage.getHeadingText();

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Hi, Bartosz Testowy");
    }

    @Test
    public void signUpTestWithUserObject() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        User user = new User();
        user.setFirstName("Bartosz");
        user.setLastName("Testowy");
        user.setPhone("333444555");
        user.setEmail(setEmailName());
        user.setPassword("123123");

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.fillSignUpFormWithObject(user);

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        loggedUserPage.getHeadingText();

        Assert.assertTrue(loggedUserPage.getHeadingText().contains(user.getLastName()));
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Hi, Bartosz Testowy");
    }

    @Test
    public void signUpWithoutProvidingAnyValues() {
        SignUpPage signUpPage = new HotelSearchPage(driver)
                .openSignUpForm();
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
        SignUpPage signUpPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setConfirmPassword("Bartosz")
                .setPhone("545455545")
                .setLastName("Testowy")
                .setEmail("email")
                .setPassword("xxx123")
                .setConfirmPassword("xxx123");

        signUpPage.clickSignUpBtn();
        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));
    }

    private String setEmailName() {
        int randomNumber = (int) (Math.random() * 1000);
        return "tester" + randomNumber + "@xd.pl";

    }
}
