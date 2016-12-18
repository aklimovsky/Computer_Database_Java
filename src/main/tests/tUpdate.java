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
    This suite verifies updating the fields of an existing computer
 */
public class tUpdate {

    private final String DEFAULT_NAME = "AAK";
    private final String DEFAULT_INTRODUCED_DATE = "2016-12-04";
    private final String DEFAULT_DISCONTINUED_DATE = "2016-12-04";
    private final String DEFAULT_COMPANY = "Apple Inc.";
    private final String DEFAULT_INTRODUCED_DATE_2 = "04 Dec 2016";
    private final String DEFAULT_DISCONTINUED_DATE_2 = "04 Dec 2016";
    private final String NEW_NAME = "NEW_NAME";
    private final String NEW_INTRODUCED_DATE = "2013-10-12";
    private final String NEW_DISCONTINUED_DATE = "2014-10-12";
    private final String NEW_COMPANY = "IBM";
    private final String NEW_INTRODUCED_DATE_2 = "12 Oct 2013";
    private final String NEW_DISCONTINUED_DATE_2 = "12 Oct 2014";

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
        Updating all of the fields should be possible
     */
    @Test
    public void updatingOfAllFields() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.checkThatComputerNameIsAbsent( NEW_NAME );

        mainPage.addAComputer( DEFAULT_NAME, DEFAULT_INTRODUCED_DATE, DEFAULT_DISCONTINUED_DATE, DEFAULT_COMPANY );

        // Set new values for existing computer
        mainPage.filter( DEFAULT_NAME );
        mainPage.editAFirstComputer( NEW_NAME, NEW_INTRODUCED_DATE, NEW_DISCONTINUED_DATE, NEW_COMPANY );

        // Check that computer's fields have changed
        mainPage.filterAndCheck( NEW_NAME, mainPage.XPATH_ONE_COMPUTER_FOUND );
        mainPage.checkFirstComputerFields( NEW_NAME, NEW_INTRODUCED_DATE_2, NEW_DISCONTINUED_DATE_2, NEW_COMPANY );

        mainPage.filterAndCheck( DEFAULT_NAME, mainPage.XPATH_NO_COMPUTERS_FOUND );
    }

    /*
        It should be possible to cancel changes
     */
    @Test
    public void cancellingTheUpdate() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.checkThatComputerNameIsAbsent( NEW_NAME );

        mainPage.addAComputer( DEFAULT_NAME, DEFAULT_INTRODUCED_DATE, DEFAULT_DISCONTINUED_DATE, DEFAULT_COMPANY );

        mainPage.filter( DEFAULT_NAME );
        mainPage.goToEditPage();

        // Set new values for existing computer
        EditComputerPage editComputerPage = new EditComputerPage( window );
        editComputerPage.fillComputerNameField( NEW_NAME );
        editComputerPage.fillIntroducedDateField( NEW_INTRODUCED_DATE );
        editComputerPage.fillDiscontinuedDateField( NEW_DISCONTINUED_DATE );
        editComputerPage.selectCompany( NEW_COMPANY );
        // Cancel the setting of new values
        window.findElement( By.xpath( editComputerPage.XPATH_CANCEL_BUTTON) ).click();

        mainPage.filterAndCheck( DEFAULT_NAME, mainPage.XPATH_ONE_COMPUTER_FOUND );
        mainPage.checkFirstComputerFields( DEFAULT_NAME, DEFAULT_INTRODUCED_DATE_2, DEFAULT_DISCONTINUED_DATE_2, DEFAULT_COMPANY );

        mainPage.filterAndCheck( NEW_NAME, mainPage.XPATH_NO_COMPUTERS_FOUND );
    }

    /*
        It should not be possible to save computer with deleted computer name
     */
    @Test
    public void deletingTheComputerName() throws Exception {

        MainPage mainPage = new MainPage( window );

        mainPage.addAComputer( DEFAULT_NAME, DEFAULT_INTRODUCED_DATE, DEFAULT_DISCONTINUED_DATE, DEFAULT_COMPANY );

        mainPage.filter( DEFAULT_NAME );
        mainPage.goToEditPage();

        // Delete computer name value
        EditComputerPage editComputerPage = new EditComputerPage( window );
        editComputerPage.fillComputerNameField( "" );
        window.findElement( By.xpath( editComputerPage.XPATH_SAVE_THIS_COMPUTER_BUTTON) ).click();

        String[] elements = new String[] { editComputerPage.XPATH_EDIT_COMPUTER_HEADING, editComputerPage.XPATH_COMPUTER_NAME_ERROR };
        Utils.checkPageContainsElements( elements );
    }


}