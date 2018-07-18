package com.epam.ta.pages;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends AbstractPage
{
	private final String BASE_URL = "https://github.com/";

	//@FindBy(xpath = "//summary[contains(@aria-label, 'Create new...')]")
	@FindBy(xpath = "//span[@class='dropdown-caret mt-1']")
	private WebElement buttonCreateNew;

	@FindBy(xpath = "//a[contains(text(), 'New repository')]")
	private WebElement linkNewRepository;

	public MainPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	public void clickOnCreateNewRepositoryButton()
	{
		buttonCreateNew.click();
		linkNewRepository.click();
	}

	@Override
	public void openPage()
	{
		driver.navigate().to(BASE_URL);
	}
}
