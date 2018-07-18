package com.epam.ta.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.ta.utils.Utils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;

public class CreateNewRepositoryPage extends AbstractPage
{
	private final String BASE_URL = "http://www.github.com/new";
	private final Logger logger = LogManager.getRootLogger();

	@FindBy(id = "repository_name")
	private WebElement inputRepositoryName;

	@FindBy(id = "repository_description")
	private WebElement inputRepositoryDescription;

	@FindBy(xpath = "//form[@id='new_repository']//button[@type='submit']")
	private WebElement butttonCreate;

	@FindBy(id = "empty-setup-new-repo-echo")
	private WebElement labelEmptyRepoSetupOption;

	@FindBy(xpath = "//a[@data-pjax='#js-repo-pjax-container']")
	private WebElement linkCurrentRepository;

	@FindBy(xpath = "//a[text()='.gitignore']")
	private WebElement gitignoreLink;


	@FindBy(xpath = "//button[@id='submit-file']")
	private WebElement commitButton;

	@FindBy(xpath = "//a[text()='Update .gitignore']")
	private WebElement commitName;

	@FindBy(xpath = "//input[@id='repository_public_false']")
	private WebElement privateRepoCheckbox;

	@FindBy(xpath = "//div[@id='form-element-creditCardNumber']//input")
	private WebElement creditCardNumberField;

	@FindBy(xpath = "//a[@id='submitButton']")
	private WebElement cardSubmitButton;

	@FindBy(xpath = "//div[@id='error-creditCardNumber']")
	private WebElement cardErrorLabel;

	public CreateNewRepositoryPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	public boolean isCurrentRepositoryEmpty()
	{
		return labelEmptyRepoSetupOption.isDisplayed();
	}

	public String createNewRepository(String repositoryName, String repositoryDescription, boolean random)
	{
		String repositoryFullName;
		if (random){
			repositoryFullName = repositoryName + Utils.getRandomString(6);
		}
		else{
			repositoryFullName = repositoryName;
		}
		inputRepositoryName.sendKeys(repositoryFullName);
		inputRepositoryDescription.sendKeys(repositoryDescription);
		butttonCreate.click();
		return repositoryFullName;
	}

	public String getCurrentRepositoryName()
	{
		return linkCurrentRepository.getText();
	}

	/*method creates .gitignore and returns true if file was commited successfully*/
	public boolean createGitignore(String text) {
		gitignoreLink.click();
		insertGitignoreText(text);
		commitButton.click();
		return commitName.isDisplayed();
	}

	private void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	/*method moves mouse to the .gitignore input code field and inserts text via robot*/
	private void insertGitignoreText(String text) {
		try {
			setClipboardData(text);
			Robot robot = new Robot();
			robot.delay(1000);
			robot.mouseMove(400,800);
			robot.delay(1000);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(1000);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*method inputs incorrect credit card number and returns error message*/
	public String incorrectCreditCard() throws InterruptedException {
		String cardNumber = Utils.getRandomNumericString(6);
		privateRepoCheckbox.click();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,1300)", "");
		sleep(5000);
		jse.executeScript("window.scrollBy(0,500)", "");
		sleep(5000);
		creditCardNumberField.sendKeys(cardNumber);
		cardSubmitButton.click();
		return cardErrorLabel.getText();
	}

	@Override
	public void openPage()
	{
		driver.navigate().to(BASE_URL);
	}

}
