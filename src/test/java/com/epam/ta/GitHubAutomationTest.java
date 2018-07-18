package com.epam.ta;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.ta.steps.Steps;

import static java.lang.Thread.sleep;

public class GitHubAutomationTest
{
	private Steps steps;
	public final static String USERNAME = "testautomationuser";
	private final String PASSWORD = "Time4Death!";
	public final static String REPO_NAME = "test-test-test-1-2-3";
	private final String GITIGNORE = ".idea/";

	@BeforeMethod(description = "Init browser")
	public void setUp()
	{
		steps = new Steps();
		steps.initBrowser();
	}

	@Test
	public void oneCanCreateProject() throws InterruptedException {
		steps.loginGithub(USERNAME, PASSWORD);
		sleep(2000);
		Assert.assertTrue(steps.createNewRepository("testRepo", "auto-generated test repo", true));
		Assert.assertTrue(steps.currentRepositoryIsEmpty());
	}

	@Test(description = "Login to Github")
	public void oneCanLoginGithub()
	{
		steps.loginGithub(USERNAME, PASSWORD);
		Assert.assertTrue(steps.isLoggedIn(USERNAME));
	}

	/*this test creates a new repo then deletes it and asserts info message after successful deletion*/
	@Test
	public void deleteRepositoryTest() {
		steps.loginGithub(USERNAME,PASSWORD);
		Assert.assertTrue(steps.createNewRepository(REPO_NAME, "auto-generated test repo", false));
		String message = steps.deleteRepo();
		Assert.assertEquals(message,"Your repository \""+USERNAME+"/"+REPO_NAME+"\" was successfully deleted.");
	}

	/*this test creates a new repo then adds .gitignore and asserts new commit*/
	@Test
	public void gitignoreCreationTest(){
		steps.loginGithub(USERNAME,PASSWORD);
		Assert.assertTrue(steps.createNewRepository("testRepo", "auto-generated test repo", true));
		Assert.assertTrue(steps.createGitignore(GITIGNORE));
	}

	/*this test changes repository to private then inputs incorrect credit card number data
	and asserts error message */
	@Test
	public void incorrectCreditCardNumberNegativeTest() throws InterruptedException {
		steps.loginGithub(USERNAME,PASSWORD);
		Assert.assertEquals(steps.incorrectCreditCard(),"Invalid Card Number");

	}

	@AfterMethod(description = "Stop Browser")
	public void stopBrowser()
	{
		steps.closeDriver();
	}

}
