import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


public class HotelSearch {

    private WebDriver driver;

    @Test
    public void searchHotel() {
        driver = ReUsable.getDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://www.kurs-selenium.pl/demo/");
        driver.findElement(By.xpath("//span[text()='Search by Hotel or City Name']")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input[@type='text']")).sendKeys("Dubai");
        driver.findElement(By.xpath("//span[@class='select2-match' and text()='Dubai']")).click();
        driver.findElement(By.xpath("//input[@name='checkin']")).sendKeys("17/04/2021");
        driver.findElement(By.xpath("//input[@name='checkout']")).click();
        driver.findElements(By.xpath("//td[@class='day ' and text()='23']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElement(By.id("travellersInput")).click();
        driver.findElement(By.id("adultPlusBtn")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()=' Search']")).click();
        List<String> hotelNames = driver.findElements(By.xpath("//h4[contains(@class,'list_title')]//b"))
                .stream()
                .map(el -> el.getAttribute("textContent"))
                .toList();

        System.out.println(hotelNames.size());
        hotelNames.forEach(System.out::println);

        Assert.assertEquals("Jumeirah Beach Hotel", hotelNames.get(0));

        driver.quit();
    }

    @Test
    public void searchHotelWithoutNameHotel() {
        driver = ReUsable.getDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://www.kurs-selenium.pl/demo/");
        driver.findElement(By.xpath("//input[@name='checkin']")).sendKeys("17/04/2021");
        driver.findElement(By.xpath("//input[@name='checkout']")).click();
        driver.findElements(By.xpath("//td[@class='day ' and text()='23']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElement(By.xpath("//button[text()=' Search']")).click();
        WebElement headingNoResults = driver.findElement(By.xpath("//h2[text()='No Results Found']"));

        Assert.assertTrue(headingNoResults.getText().contains("No Results Found"));
        Assert.assertEquals(headingNoResults.getAttribute("textContent"), "No Results Found");

        driver.quit();
    }
}
