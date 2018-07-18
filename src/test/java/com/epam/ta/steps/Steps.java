package com.epam.ta.steps;

import java.util.concurrent.TimeUnit;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.pages.DeleteRepositoryPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.epam.ta.pages.CreateNewRepositoryPage;
import com.epam.ta.pages.LoginPage;
import com.epam.ta.pages.MainPage;

public class Steps
{
	private WebDriver driver;

	private final Logger logger = LogManager.getRootLogger();

	public void initBrowser()
	{
		driver = DriverSingleton.getDriver();
	}

	public void closeDriver()
	{
		DriverSingleton.closeDriver();
	}

	public void loginGithub(String username, String password)
	{
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage();
		loginPage.login(username, password);
	}

	public boolean isLoggedIn(String username)
	{
		LoginPage loginPage = new LoginPage(driver);
		String actualUsername = loginPage.getLoggedInUserName().trim().toLowerCase();
		logger.info("Actual username: " + actualUsername);
		return actualUsername.equals(username);
	}

	public boolean createNewRepository(String repositoryName, String repositoryDescription, boolean random)
	{
		MainPage mainPage = new MainPage(driver);
		mainPage.clickOnCreateNewRepositoryButton();
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		String expectedRepoName = createNewRepositoryPage.createNewRepository(repositoryName, repositoryDescription, random);
		return expectedRepoName.equals(createNewRepositoryPage.getCurrentRepositoryName());
	}


	public boolean currentRepositoryIsEmpty()
	{
		CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
		return createNewRepositoryPage.isCurrentRepositoryEmpty();
	}

	/* this step calls method deleteRepository() and returns message that
	appears after successful deletion to the test*/
	public String deleteRepo()  {
		DeleteRepositoryPage drPage = new DeleteRepositoryPage(driver);
		String message = drPage.deleteRepository();
		return message;
	}

	/*this step calls method createGitignore(String) and returns true if a new file was commited*/
	public boolean createGitignore(String text){
		CreateNewRepositoryPage cnrPage = new CreateNewRepositoryPage(driver);
		return cnrPage.createGitignore(text);
	}

	/*this step calls method incorrectCreditCard and returns error message*/
	public String incorrectCreditCard() throws InterruptedException {
		MainPage mainPage = new MainPage(driver);
		mainPage.clickOnCreateNewRepositoryButton();
		CreateNewRepositoryPage cnrPage = new CreateNewRepositoryPage(driver);
		return cnrPage.incorrectCreditCard();
	}
}
