package pl.seleniumdemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumdemo.pages.HotelSearchPage;
import pl.seleniumdemo.pages.ResultsPage;

import java.util.List;

public class HotelSearchTest extends BaseTest {
    @Test
    public void searchHotelTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        //chainowanie metod
        List<String> hotelNames = hotelSearchPage
                .setCityName("Dubai")
                .setDates("27/04/2023", "01/05/2023")
                .setTravellers(1, 2)
                .performSearch().getHotelNames();

        hotelNames.forEach(System.out::println);

        Assert.assertEquals("Jumeirah Beach Hotel", hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower", hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNames.get(3));
    }

    @Test
    public void searchHotelWithoutNameTesT() {

        ResultsPage resultsPage = new HotelSearchPage(driver)
                .setDates("27/04/2023", "01/05/2023")
                .setTravellers(0, 1)
                .performSearch();

        final var headingText = resultsPage.getHeadingText();
        Assert.assertTrue(resultsPage.getResultHeading().isDisplayed());
        Assert.assertEquals(headingText, "No Results Found");
    }
}
