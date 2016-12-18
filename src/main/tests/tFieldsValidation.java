package main.tests;

import main.pageobjects.AddAComputerPage;
import main.pageobjects.MainPage;
import main.resources.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
    This suite verifies possible values for fields of a new computer being created
 */
public class tFieldsValidation {

    private final String DEFAULT_NAME = "AAK";
    private final String LONG_NAME = "a1[|]~<--@/*$%^&#*/()?>,.*/\\\"'`|/\\,;:&<>^*?«»àäåçèëìíîðñòôöыф♣☺♂^\\M\\r百科";
    private final String INTRODUCED_DATE_1 = "2016-12-0a";
    private final String INTRODUCED_DATE_2 = "2016-13-04";
    private final String DISCONTINUED_DATE_1 = "2016.12-04";
    private final String DISCONTINUED_DATE_2 = "2016-12-32";

    public static WebDriver window;

    @BeforeMethod
    private void TestSetup() throws Exception {

        window = Utils.openComputerDatabaseURL();
    }

    @AfterMethod
    private void TestTeardown() {

        window.close();
        window.quit();
    }

    /*
        Add a computer form should have a number of certain input fields, labels and buttons.
     */
    @Test
    public void defaultStateOfAddAComputerForm() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.goToAddAComputerPage();

        AddAComputerPage addAComputerPage = new AddAComputerPage( window );

        // Input fields
        String[] inputs = new String[]{ addAComputerPage.XPATH_COMPUTER_NAME_INPUT, addAComputerPage.XPATH_INTRODUCED_DATE_INPUT, addAComputerPage.XPATH_DISCONTINUED_DATE_INPUT};
        Utils.checkPageContainsElements( inputs );

        // Info labels near the fields
        String[] labels = new String[]{ addAComputerPage.XPATH_COMPUTER_NAME_LABEL, addAComputerPage.XPATH_INTRODUCED_DATE_LABEL, addAComputerPage.XPATH_DISCONTINUED_DATE_LABEL};
        Utils.checkPageContainsElements( labels );

        // Company name selector
        window.findElement( By.xpath ( addAComputerPage.XPATH_COMPANY_SELECT) );

        // Create this computer and Cancel button should be enabled
        boolean createButtonEnabled = window.findElement( By.xpath ( addAComputerPage.XPATH_CREATE_THIS_COMPUTER_BUTTON ) ).isEnabled();
        if( !createButtonEnabled )
            Assert.fail( addAComputerPage.XPATH_CREATE_THIS_COMPUTER_BUTTON + " button is disabled!");

        boolean cancelButton = window.findElement( By.xpath (addAComputerPage.XPATH_CANCEL_BUTTON) ).isEnabled();
        if( !cancelButton )
            Assert.fail( addAComputerPage.XPATH_CANCEL_BUTTON + " button is disabled!");
    }

    /*
        Computer name can contain any symbols.
     */
    @Test
    public void computerNameCanContainAnySymbols() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.addAComputer( LONG_NAME );
    }

    /*
        It should NOT be possible to add computer with whitespace-only name
     */
    @Test
    public void computerWithWhitespaceOnlyNameCanNotBeAdded() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.goToAddAComputerPage();

        AddAComputerPage addAComputerPage = new AddAComputerPage( window );
        addAComputerPage.fillComputerNameField( " " );
        window.findElement( By.xpath( addAComputerPage.XPATH_CREATE_THIS_COMPUTER_BUTTON ) ).click();

        window.findElement( By.xpath( addAComputerPage.XPATH_ADD_A_COMPUTER_HEADING ) );
        window.findElement( By.xpath( addAComputerPage.XPATH_COMPUTER_NAME_ERROR) );
    }

    /*
        Date should not contain any symbols except digits and hyphen
     */
    @Test
    public void invalidSymbolsInDateFields() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.goToAddAComputerPage();

        AddAComputerPage addAComputerPage = new AddAComputerPage( window );
        addAComputerPage.fillComputerNameField( DEFAULT_NAME );
        // Input introduced and discontinued dates containing letter and point symbol
        addAComputerPage.fillIntroducedDateField( INTRODUCED_DATE_1 );
        addAComputerPage.fillDiscontinuedDateField( DISCONTINUED_DATE_1 );
        window.findElement( By.xpath( addAComputerPage.XPATH_CREATE_THIS_COMPUTER_BUTTON ) ).click();

        window.findElement( By.xpath( addAComputerPage.XPATH_ADD_A_COMPUTER_HEADING ) );
        String[] labels = new String[] { addAComputerPage.XPATH_INTRODUCED_DATE_ERROR, addAComputerPage.XPATH_DISCONTINUED_DATE_ERROR};
        Utils.checkPageContainsElements( labels );
    }

    /*
        Invalid date should not be accepted
     */
    @Test
    public void invalidValuesForMonthAndDay() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.goToAddAComputerPage();

        AddAComputerPage addAComputerPage = new AddAComputerPage( window );
        addAComputerPage.fillComputerNameField( DEFAULT_NAME );
        // Input impossible dates - 13th month and 32nd day
        addAComputerPage.fillIntroducedDateField( INTRODUCED_DATE_2 );
        addAComputerPage.fillDiscontinuedDateField( DISCONTINUED_DATE_2 );
        window.findElement( By.xpath( addAComputerPage.XPATH_CREATE_THIS_COMPUTER_BUTTON ) ).click();

        window.findElement( By.xpath( addAComputerPage.XPATH_ADD_A_COMPUTER_HEADING ) );
        String[] labels = new String[] { addAComputerPage.XPATH_INTRODUCED_DATE_ERROR, addAComputerPage.XPATH_DISCONTINUED_DATE_ERROR};
        Utils.checkPageContainsElements( labels );
    }

    /*
        Cancelled computer should not be added to the list
     */
    @Test
    public void cancellingAddingTheComputerWithValidData() throws Exception {

        MainPage mainPage = new MainPage( window );
        mainPage.checkThatComputerNameIsAbsent( DEFAULT_NAME );

        mainPage.goToAddAComputerPage();

        AddAComputerPage addAComputerPage = new AddAComputerPage( window );
        window.findElement( By.xpath( addAComputerPage.XPATH_CANCEL_BUTTON) ).click();
        window.findElement( By.xpath( mainPage.XPATH_ADD_A_NEW_COMPUTER_BUTTON ) );

        mainPage.filterAndCheck( DEFAULT_NAME, mainPage.XPATH_NO_COMPUTERS_FOUND );
    }


}