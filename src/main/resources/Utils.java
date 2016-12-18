package main.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class Utils {

    private static WebDriver driver;
    public static final int defaultTimeout = 10;
    // the time after we assume that the list of filtered results is ready
    public static final int filterTimeout = 3;

    private static String ComputerDatabaseURL = "http://computer-database.herokuapp.com/computers";

    /*
        Create Chrome window, open Computer database page
     */
    public static WebDriver openComputerDatabaseURL() throws Exception {

        driver = new ChromeDriver();
        Assert.assertNotNull( driver, "Can not create Chrome window" );

        setDefaultTimeout();

        driver.get(ComputerDatabaseURL);
        driver.manage().window().maximize();

        return driver;
    }

    /*
        Check that current page contains certain web elements
     */
    public static void checkPageContainsElements(String[] elements) {

        for ( String element: elements ) {
            driver.findElement( By.xpath( element ));
        }
    }

    /*
        Set global Selenium timeout to a value suitable for filtering
     */
    public static void setFilterTimeout() {

        driver.manage().timeouts().implicitlyWait( Utils.filterTimeout, TimeUnit.SECONDS);
    }

    /*
        Set global Selenium timeout to a default value
     */
    public static void setDefaultTimeout() {

        driver.manage().timeouts().implicitlyWait( Utils.defaultTimeout, TimeUnit.SECONDS);
    }
}
