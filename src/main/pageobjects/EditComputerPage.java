package main.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/*
    Page for editing an existing computer
 */
public class EditComputerPage {

    public static final String XPATH_EDIT_COMPUTER_HEADING = "//h1[text()='Edit computer']";
    public static final String XPATH_DELETE_THIS_COMPUTER_BUTTON = "//input[@value='Delete this computer']";
    private final String XPATH_COMPUTER_NAME_INPUT = "//input[@id='name']";
    public final String XPATH_COMPUTER_NAME_ERROR = XPATH_COMPUTER_NAME_INPUT + "/ancestor::div[contains(@class,'error')]";
    private final String XPATH_INTRODUCED_DATE_INPUT = "//input[@id='introduced']";
    private final String XPATH_DISCONTINUED_DATE_INPUT = "//input[@id='discontinued']";
    private final String XPATH_COMPANY_SELECT = "//select[@id='company']";
    public final String XPATH_SAVE_THIS_COMPUTER_BUTTON = "//input[@value='Save this computer']";
    public final String XPATH_CANCEL_BUTTON = "//a[text()='Cancel']";

    private WebDriver window;

    public EditComputerPage(WebDriver createdWindow) {
        window = createdWindow;
    }

    public void fillComputerNameField( String name ) {

        window.findElement( By.xpath(XPATH_COMPUTER_NAME_INPUT)).clear();
        window.findElement( By.xpath(XPATH_COMPUTER_NAME_INPUT)).sendKeys( name );
    }

    public void fillIntroducedDateField( String date ) {

        window.findElement( By.xpath(XPATH_INTRODUCED_DATE_INPUT)).clear();
        window.findElement( By.xpath(XPATH_INTRODUCED_DATE_INPUT)).sendKeys( date );
    }

    public void fillDiscontinuedDateField( String date ) {

        window.findElement( By.xpath(XPATH_DISCONTINUED_DATE_INPUT)).clear();
        window.findElement( By.xpath(XPATH_DISCONTINUED_DATE_INPUT)).sendKeys( date );
    }

    public void selectCompany( String company ) {

        Select companySelector = new Select( window.findElement( By.xpath(XPATH_COMPANY_SELECT)));
        companySelector.selectByVisibleText( company );
    }


}
