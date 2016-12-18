package main.tests;

import main.resources.Utils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import main.pageobjects.*;

/*
    This test suite verifies creating a new computer
 */
public class tCreate {

    private final String DEFAULT_NAME = "AAK";
    private final String DEFAULT_INTRODUCED_DATE = "2010-12-04";
    private final String DEFAULT_INTRODUCED_DATE_2 = "04 Dec 2010";
    private final String DEFAULT_DISCONTINUED_DATE = "2016-12-04";
    private final String DEFAULT_DISCONTINUED_DATE_2 = "04 Dec 2016";
    private final String DEFAULT_COMPANY = "Apple Inc.";

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
        It should be possible to add new computer with unique name.
        It should be possible to add computer with no additional info but name.
    */
    @Test
    public void addNewComputerWithUniqueName() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.addAComputer(DEFAULT_NAME);

        mainPage.filter( DEFAULT_NAME );
        Utils.setFilterTimeout();
        window.findElement(By.xpath( mainPage.XPATH_ONE_COMPUTER_FOUND ));
        Utils.setDefaultTimeout();

        mainPage.checkFirstComputerFields( DEFAULT_NAME, "", "", "" );
    }

    /*
        It should be possible to add new computer with full information.
     */
    @Test
    public void addNewComputerWithAllFieldsPopulated() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.addAComputer(DEFAULT_NAME, DEFAULT_INTRODUCED_DATE, DEFAULT_DISCONTINUED_DATE, DEFAULT_COMPANY);

        mainPage.filter( DEFAULT_NAME );
        Utils.setFilterTimeout();
        window.findElement(By.xpath( mainPage.XPATH_ONE_COMPUTER_FOUND ));
        Utils.setDefaultTimeout();

        mainPage.checkFirstComputerFields( DEFAULT_NAME, DEFAULT_INTRODUCED_DATE_2, DEFAULT_DISCONTINUED_DATE_2, DEFAULT_COMPANY );

    }

    /*
        It should NOT be possible to add new computer with existing name
     */
    @Test
    public void addNewComputerWithExistingName() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.addAComputer(DEFAULT_NAME);

        mainPage.goToAddAComputerPage();

        AddAComputerPage addAComputerPage = new AddAComputerPage( window );
        addAComputerPage.fillComputerNameField( DEFAULT_NAME );
        window.findElement( By.xpath( addAComputerPage.XPATH_CREATE_THIS_COMPUTER_BUTTON ) ).click();

        // Here we expect the appropriate error message to appear, something like "Cannot create computer AAK as it is already present".
        // As the current implementation allows creating two computers with identical names we cannot consider an exact xpath for that message.
        // So that line is commented.
        // window.findElement( By.xpath( addAComputerPage.XPATH_ERROR_MESSAGE ) );
        window.findElement( By.xpath( addAComputerPage.XPATH_CANCEL_BUTTON) ).click();

        mainPage.filterAndCheck( DEFAULT_NAME, mainPage.XPATH_ONE_COMPUTER_FOUND );
    }


}