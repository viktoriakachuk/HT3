package com.epam.ta.pages;

import com.epam.ta.GitHubAutomationTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static com.epam.ta.GitHubAutomationTest.REPO_NAME;
import static com.epam.ta.GitHubAutomationTest.USERNAME;
import static java.lang.Thread.sleep;

public class DeleteRepositoryPage extends AbstractPage {
    private final String BASE_URL = "https://github.com/"+USERNAME+"/"+REPO_NAME+"/settings";
    private final Logger logger = LogManager.getRootLogger();

    @FindBy(xpath = "//a[text()='\n" +
            "      Settings\n']")
    private WebElement settingsLink;

    @FindBy(xpath = "//summary[text()='\n" +
            "      Delete this repository\n" +
            "    ']")
    private WebElement deleteButton;

    @FindBy(xpath = "//div[@class='flash flash-full flash-notice']//div")
    private WebElement deletedMessage;

    public DeleteRepositoryPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    /*method deletes repository and returns info message*/
    public String deleteRepository()  {
        settingsLink.click();
        deleteButton.click();
        insertAndConfirm(REPO_NAME); //robot method call
        return deletedMessage.getText();
    }

    private void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    /*method inserts repository name and clicks enter via robot*/
    private void insertAndConfirm(String name) {
        try {
            setClipboardData(name);
            Robot robot = new Robot();
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(1000);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public void openPage() {
        driver.navigate().to(BASE_URL);
    }
}
