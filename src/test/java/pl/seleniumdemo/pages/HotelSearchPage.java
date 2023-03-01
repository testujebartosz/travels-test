package pl.seleniumdemo.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HotelSearchPage {

    @FindBy(xpath = "//span[text()='Search by Hotel or City Name']")
    private WebElement searchHotelSpan;

    @FindBy(xpath = "//div[@id='select2-drop']//input[@type='text']")
    private WebElement searchHotelInput;

    @FindBy(xpath = "//input[@name='checkin']")
    private WebElement checkInInput;

    @FindBy(xpath = "//input[@name='checkout']")
    private WebElement checkOutInput;

    @FindBy(id = "travellersInput")
    private WebElement travellersInput;

    @FindBy(id = "adultPlusBtn")
    private WebElement adultPlusBtn;

    @FindBy(id = "childPlusBtn")
    private WebElement childPlusBtn;

    @FindBy(xpath = "//button[text()=' Search']")
    private WebElement searchBtn;

    @FindBy(xpath = "//li[@id='li_myaccount']")
    private List<WebElement> myAccountLink;

    @FindBy(xpath = "//a[text()='  Sign Up']")
    private List<WebElement> signUpLink;

    private WebDriver driver;

    private static final Logger logger = LogManager.getLogger();

    public HotelSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void setCityName(final String cityName) {
        logger.info("Setting city "  + cityName);
        searchHotelSpan.click();
        searchHotelInput.sendKeys(cityName);
        final String xpath = String.format("//span[@class='select2-match' and text()='%s']", cityName);
        driver.findElement(By.xpath(xpath)).click();
        logger.info("Setting city done");
    }

    public void setDates(final String checkIn, final String checkOut) {
        logger.info("Setting dates check-in:" + checkIn + " check-out " + checkOut);
        checkInInput.sendKeys(checkIn);
        checkOutInput.sendKeys(checkOut);
        logger.info("Setting dates done");
    }

    public void setTravellers(final int adultsToAdd, final int childToAdd) {
        travellersInput.click();
        addTraveler(adultPlusBtn, adultsToAdd);
        addTraveler(childPlusBtn, childToAdd);
    }

    private void addTraveler(WebElement travelerBtn, final int numberOfTravels) {
        for (int i = 0; i < numberOfTravels; i++) {
            travelerBtn.click();
        }
    }

    public void performSearch() {
        searchBtn.click();
    }

    public void openSignUpForm() {
        myAccountLink.stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        signUpLink.get(1).click();
    }
}
