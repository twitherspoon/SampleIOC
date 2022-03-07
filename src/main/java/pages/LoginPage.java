package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import commons.SeleniumHelper;

public class LoginPage{

	SeleniumHelper helper;

	@FindBy(how = How.ID, using = "Username")
	WebElement userNameInput;

	@FindBy(how = How.ID, using = "Password")
	WebElement passwordInput;

	@FindBy(how = How.ID, using = "LoginButton")
	WebElement loginBtn;

	public LoginPage(SeleniumHelper helper){
		this.helper = helper;
		PageFactory.initElements(helper.driver, this);
	}

	public void typeUsername(String username) {
		helper.findElementAndSendkey(userNameInput,username);
	}

	public void typePassword(String password){
		helper.findElementAndSendkey(passwordInput,password);
	}

	public void clickLoginBtn() {
		helper.findElementAndClick(loginBtn);
	}
}
