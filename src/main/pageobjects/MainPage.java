package main.pageobjects;


import main.resources.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/*
    Page with the list of the computers
 */
public class MainPage {

    private final String XPATH_FILTER_INPUT = "//input[@id='searchbox']";
    private final String XPATH_FILTER_BUTTON = "//input[@id='searchsubmit']";
    public final String XPATH_NO_COMPUTERS_FOUND = "//h1[text()='No computers found']";
    public final String XPATH_ONE_COMPUTER_FOUND = "//h1[text()='One computer found']";
    public final String XPATH_DELETED_MESSAGE = "//div[./text()[contains(.,'Computer has been deleted')]]";
    private final String XPATH_CREATED_MESSAGE = "//div[./text()[contains(.,'has been created')]]";
    private final String XPATH_UPDATED_MESSAGE = "//div[./text()[contains(.,'has been updated')]]";
    public final String XPATH_ADD_A_NEW_COMPUTER_BUTTON = "//a[text()='Add a new computer']";
    private final String XPATH_COMPUTERS_TABLE = "//table[contains(@class,'computers')]";
    private final String XPATH_COMPUTERS = XPATH_COMPUTERS_TABLE + "/tbody/tr";
    private final String XPATH_FIRST_COMPUTER_NAME = XPATH_COMPUTERS + "[1]/td[1]/a";
    private final String XPATH_FIRST_COMPUTER_INTRODUCED_DATE = XPATH_COMPUTERS + "[1]/td[2]";
    private final String XPATH_FIRST_COMPUTER_DISCONTINUED_DATE = XPATH_COMPUTERS + "[1]/td[3]";
    private final String XPATH_FIRST_COMPUTER_COMPANY_NAME = XPATH_COMPUTERS + "[1]/td[4]";

    private WebDriver window;

    public MainPage(WebDriver createdWindow) {
        window = createdWindow;
    }

    /*
        Check that a computer with a certain name is not present in the database.
        If yes, delete it.
     */
    public void checkThatComputerNameIsAbsent(String name) {

        Utils.setFilterTimeout();

        while( true ) {
            filter( name );
            List records = window.findElements(By.xpath(XPATH_NO_COMPUTERS_FOUND));
            if (records.size() > 0 ) break;
            deleteFirstComputer();
        }

       Utils.setDefaultTimeout();
    }

    /*
        Input a computer name in the input field and press "Filter by name" button
     */
    public void filter( String name ) {

        WebElement inputField = window.findElement(By.xpath( XPATH_FILTER_INPUT ));
        inputField.clear();
        inputField.sendKeys( name );

        WebElement submitButton = window.findElement(By.xpath( XPATH_FILTER_BUTTON ));
        submitButton.click();
    }

    /*
        Filter the list by computer name and check that a certain web element is present on the page
     */
    public void filterAndCheck( String name, String element ) {

        this.filter( name );

        Utils.setFilterTimeout();
        window.findElement( By.xpath( element ));
        Utils.setDefaultTimeout();
    }

    /*
        Delete the first computer in the displayed list
     */
    private void deleteFirstComputer() {

        window.findElement( By.xpath(XPATH_FIRST_COMPUTER_NAME) ).click();
        window.findElement( By.xpath( EditComputerPage.XPATH_DELETE_THIS_COMPUTER_BUTTON ) ).click();
        window.findElement( By.xpath( this.XPATH_DELETED_MESSAGE ) ).click();
    }

    /*
        Press Add a new computer button and wait until Add a computer page is displayed
     */
    public void goToAddAComputerPage() {

        window.findElement(By.xpath( XPATH_ADD_A_NEW_COMPUTER_BUTTON )).click();
        window.findElement(By.xpath( AddAComputerPage.XPATH_ADD_A_COMPUTER_HEADING ));
    }

    /*
        Press Add a computer button, fill in the Computer name field, press Save button.
        Wait until an appropriate message is displayed.
     */
    public void addAComputer(String name) {

        goToAddAComputerPage();
        AddAComputerPage addAComputerPage = new AddAComputerPage( window );
        addAComputerPage.fillComputerNameField( name );
        window.findElement( By.xpath( addAComputerPage.XPATH_CREATE_THIS_COMPUTER_BUTTON ) ).click();
        window.findElement( By.xpath( XPATH_CREATED_MESSAGE ) );
    }

    /*
        Press Add a computer button, fill in all the fields, press Create this computer button.
        Wait until an appropriate message is displayed.
     */
    public void addAComputer( String name, String introduced, String discontinued, String company ) {

        goToAddAComputerPage();

        AddAComputerPage addAComputerPage = new AddAComputerPage( window );

        addAComputerPage.fillComputerNameField( name );
        addAComputerPage.fillIntroducedDateField( introduced );
        addAComputerPage.fillDiscontinuedDateField( discontinued);
        addAComputerPage.selectCompany( company );

        window.findElement( By.xpath( addAComputerPage.XPATH_CREATE_THIS_COMPUTER_BUTTON ) ).click();
        window.findElement( By.xpath( XPATH_CREATED_MESSAGE ) );
    }

    /*
        Verify that the first computer in the list has appropriate name, introduced and discontinued date
        and company name
     */
    public void checkFirstComputerFields(String name, String introduced, String discontinued, String company) {

        String actual_name = window.findElement( By.xpath(XPATH_FIRST_COMPUTER_NAME)).getText();
        Assert.assertEquals( actual_name, name, "First computer name is " + actual_name + " instead of " + name );

        String actual_introduced = window.findElement( By.xpath(XPATH_FIRST_COMPUTER_INTRODUCED_DATE)).getText();
        if( introduced == "" )
            Assert.assertEquals( actual_introduced, "-", "First computer introduced date is " + actual_introduced + " instead of empty" );
        else
            Assert.assertEquals( actual_introduced, introduced, "First computer introduced date is " + actual_introduced + " instead of " + introduced );

        String actual_discontinued = window.findElement( By.xpath(XPATH_FIRST_COMPUTER_DISCONTINUED_DATE)).getText();
        if( discontinued == "" )
            Assert.assertEquals( actual_discontinued, "-", "First computer discontinued date is " + actual_discontinued + " instead of empty" );
        else
            Assert.assertEquals( actual_discontinued, discontinued, "First computer discontinued date is " + actual_discontinued + " instead of " + discontinued );

        String actual_company = window.findElement( By.xpath(XPATH_FIRST_COMPUTER_COMPANY_NAME)).getText();
        if( company == "" )
            Assert.assertEquals( actual_company, "-", "First computer company name date is " + actual_company + " instead of empty" );
        else
            Assert.assertEquals( actual_company, company, "First computer company name is " + actual_company + " instead of " + company );

    }

    /*
        Press on the first computer name and wait until Edit computer page is displayed
     */
    public void goToEditPage() {

        window.findElement(By.xpath( XPATH_FIRST_COMPUTER_NAME )).click();
        window.findElement(By.xpath( EditComputerPage.XPATH_EDIT_COMPUTER_HEADING ));
    }

    /*
        Press on the first computer name in the list, fill in all the fields, press Save this computer button.
        Wait until an appropriate message is displayed.
     */
    public void editAFirstComputer( String name, String introduced, String discontinued, String company ) {

        goToEditPage();

        EditComputerPage editComputerPage = new EditComputerPage( window );

        editComputerPage.fillComputerNameField( name );
        editComputerPage.fillIntroducedDateField( introduced );
        editComputerPage.fillDiscontinuedDateField( discontinued );
        editComputerPage.selectCompany( company );

        window.findElement( By.xpath( editComputerPage.XPATH_SAVE_THIS_COMPUTER_BUTTON ) ).click();
        window.findElement( By.xpath( XPATH_UPDATED_MESSAGE ) );
    }

}
