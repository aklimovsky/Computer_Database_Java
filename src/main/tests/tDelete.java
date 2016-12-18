package main.tests;

import main.pageobjects.EditComputerPage;
import main.pageobjects.MainPage;
import main.resources.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
    This suite verifies deleting of an existing computer
 */
public class tDelete {

    private final String DEFAULT_NAME = "AAK";
    private final String DEFAULT_INTRODUCED_DATE = "2016-12-04";
    private final String DEFAULT_DISCONTINUED_DATE = "2016-12-04";
    private final String DEFAULT_COMPANY = "Apple Inc.";
    private final String DEFAULT_INTRODUCED_DATE_2 = "04 Dec 2016";
    private final String DEFAULT_DISCONTINUED_DATE_2 = "04 Dec 2016";


    public static WebDriver window;

    @BeforeMethod
    private void TestSetup() throws Exception {

        window = Utils.openComputerDatabaseURL();

        MainPage mainPage = new MainPage( window );
        mainPage.checkThatComputerNameIsAbsent( DEFAULT_NAME );
    }

    @AfterMethod
    private void TestTeardown() {

        window.close();
        window.quit();
    }

    /*
        It should be possible to delete existing computer
     */
    @Test
    public void deletingTheComputer() throws Exception {

        MainPage mainPage = new MainPage( window );

        mainPage.addAComputer( DEFAULT_NAME, DEFAULT_INTRODUCED_DATE, DEFAULT_DISCONTINUED_DATE, DEFAULT_COMPANY );

        mainPage.filter( DEFAULT_NAME );
        mainPage.goToEditPage();

        window.findElement( By.xpath( EditComputerPage.XPATH_DELETE_THIS_COMPUTER_BUTTON ) ).click();
        window.findElement( By.xpath( mainPage.XPATH_DELETED_MESSAGE) ).click();

        mainPage.filterAndCheck( DEFAULT_NAME, mainPage.XPATH_NO_COMPUTERS_FOUND );
    }

}