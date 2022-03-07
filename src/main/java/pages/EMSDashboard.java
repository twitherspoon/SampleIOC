package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import commons.SeleniumHelper;

public class EMSDashboard{

	SeleniumHelper helper;

	@FindBy(how = How.XPATH, using = "//h1/span[text()='Dashboard']")
	WebElement dashboardHeading;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Sports']")
	WebElement sportSectionHeading;

	@FindBy(how = How.XPATH, using = "//span[text()='Sport']")
	WebElement sportNav;

	public EMSDashboard(SeleniumHelper helper){
		this.helper = helper;
		PageFactory.initElements(helper.driver, this);
	}
	
	public boolean dashboardPageLoad() {
		try {
			helper.waitForElementPresent(dashboardHeading,10);
			return true;
		}
		catch(Exception e) {
			return false;
		}

	}
	
	public void clickSportsNavLink() {
		helper.findElementAndClick(sportNav);
	}
	
	public boolean sportsSectionLoad() {
		try {
			helper.waitForElementPresent(sportSectionHeading,10);
			return true;
		}
		catch(Exception e) {
			return false;
		}

	}
}
