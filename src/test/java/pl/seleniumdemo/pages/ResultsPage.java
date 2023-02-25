package pl.seleniumdemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ResultsPage {

    @FindBy(xpath = "//h4[contains(@class,'list_title')]//b")
    private List<WebElement> hotelList;

    @FindBy(xpath = "//h2[text()='No Results Found']")
    private WebElement resultHeading;

    public ResultsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public List<String> getHotelNames() {
        return hotelList.stream()
                .map(el -> el.getAttribute("textContent"))
                .toList();
    }

    public String getHeadingText() {
        return resultHeading.getText();
    }

    public WebElement getResultHeading() {
        return resultHeading;
    }


}
