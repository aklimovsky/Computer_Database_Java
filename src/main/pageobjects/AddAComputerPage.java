package main.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/*
    Page for adding a new computer
 */
public class AddAComputerPage {

    public static final String XPATH_ADD_A_COMPUTER_HEADING = "//h1[text()='Add a computer']";
    public final String XPATH_COMPUTER_NAME_INPUT = "//input[@id='name']";
    public final String XPATH_COMPUTER_NAME_LABEL = XPATH_COMPUTER_NAME_INPUT + "/../span[text()='Required']";
    public final String XPATH_COMPUTER_NAME_ERROR = XPATH_COMPUTER_NAME_INPUT + "/ancestor::div[contains(@class,'error')]";
    public final String XPATH_INTRODUCED_DATE_INPUT = "//input[@id='introduced']";
    public final String XPATH_INTRODUCED_DATE_LABEL = XPATH_INTRODUCED_DATE_INPUT + "/../span[text()=\"Date ('yyyy-MM-dd')\"]";
    public final String XPATH_INTRODUCED_DATE_ERROR = XPATH_INTRODUCED_DATE_INPUT + "/ancestor::div[contains(@class,'error')]";
    public final String XPATH_DISCONTINUED_DATE_INPUT = "//input[@id='discontinued']";
    public final String XPATH_DISCONTINUED_DATE_LABEL = XPATH_DISCONTINUED_DATE_INPUT + "/../span[text()=\"Date ('yyyy-MM-dd')\"]";
    public final String XPATH_DISCONTINUED_DATE_ERROR = XPATH_DISCONTINUED_DATE_INPUT + "/ancestor::div[contains(@class,'error')]";
    public final String XPATH_COMPANY_SELECT = "//select[@id='company']";
    public static final String XPATH_CREATE_THIS_COMPUTER_BUTTON = "//input[@value='Create this computer']";
    public final String XPATH_CANCEL_BUTTON = "//a[text()='Cancel']";

    private WebDriver window;

    public AddAComputerPage(WebDriver createdWindow) {
        window = createdWindow;
    }

    public void fillComputerNameField( String name ) {

        window.findElement( By.xpath(XPATH_COMPUTER_NAME_INPUT)).sendKeys( name );
    }

    public void fillIntroducedDateField( String date ) {

        window.findElement( By.xpath(XPATH_INTRODUCED_DATE_INPUT)).sendKeys( date );
    }

    public void fillDiscontinuedDateField( String date ) {

        window.findElement( By.xpath(XPATH_DISCONTINUED_DATE_INPUT)).sendKeys( date );
    }

    public void selectCompany( String company ) {

        Select companySelector = new Select( window.findElement( By.xpath(XPATH_COMPANY_SELECT)));
        companySelector.selectByVisibleText( company );
    }

}
