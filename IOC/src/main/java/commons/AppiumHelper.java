package commons;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;



public class AppiumHelper {
	
	//public static AppiumDriver<MobileElement> driver;
	public static AppiumDriver<MobileElement> driver;
	//public static RemoteWebDriver remotedriver;
	int implicitWait = 30;
	int pageLoadTime = 30;
	int explicitWait = 30;
	DesiredCapabilities caps;
	String bsusername,bskey,app,device,osversion,project,build,name,browserstackurl,appiumurl,apppackage,appactivity,execmode;
	
	
	public AppiumHelper(String appiumurl,String apppackage,String appactivity)
	{
		this.appiumurl=appiumurl;
		this.apppackage=apppackage;
		this.appactivity=appactivity;
	}
	
	
	public AppiumHelper(String browserstackuser,String browserstackkey,String appurl,String device,String os,String project,String build,String test,String browserstackurl,String execmode)
	{
		this.bsusername=browserstackuser;
		this.bskey=browserstackkey;
		this.app=appurl;
		this.device=device;
		this.osversion=os;
		this.project=project;
		this.build=build;
		this.name=test;
		this.browserstackurl=browserstackurl;
		this.execmode=execmode;
	}
	
	
	public void setCapabilities() throws MalformedURLException
	{
		caps=new DesiredCapabilities();
		caps.setCapability("browserstack.user", bsusername);
		caps.setCapability("browserstack.key", bskey);
		caps.setCapability("app", app);
		caps.setCapability("device", device);
		caps.setCapability("os_version", osversion);
		caps.setCapability("project", project);
		caps.setCapability("build", build);
		caps.setCapability("name", name);
		if(execmode.equalsIgnoreCase("Android"))
		{		
			driver = new AppiumDriver<MobileElement>(new URL(browserstackurl), caps);
		}
		else if(execmode.equalsIgnoreCase("iOS"))
		{
			caps.setCapability("automationName", "XCUITest");
			driver = new AppiumDriver<MobileElement>(
	        		new URL("http://hub-cloud.browserstack.com/wd/hub"), caps);
		}
			

	}
	

	
	
	public void sendkey(MobileElement element,String text)
	{
		
		new WebDriverWait(driver, explicitWait)
		.withTimeout(Duration.ofSeconds(explicitWait))
		.pollingEvery(Duration.ofSeconds(1))
		.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(text);
		
	}
	
	public void assertText(MobileElement element, String expectedText) {
		String actualText = element.getText().trim();
		Assert.assertEquals(actualText, expectedText);
	}
	
	public void clickELement(MobileElement element)
	{
		new WebDriverWait(driver, explicitWait)
		.withTimeout(Duration.ofSeconds(explicitWait))
		.pollingEvery(Duration.ofSeconds(1))
		.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	public void close() {
		driver.quit();
	}
}
	


