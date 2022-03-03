package commons;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;


public class SeleniumHelper {

	public static WebDriver driver;
	int implicitWait = 30;
	int pageLoadTime = 30;
	int explicitWait = 30;
	private String browser;
	private String url;


	public SeleniumHelper(String browser, String url) {
		this.browser = browser;
		this.url = url;
	}

	public void assertText(WebElement element, String expectedText) {
		String actualText = element.getText().trim();
		Assert.assertEquals(actualText, expectedText);
	}

	public void initialization() {
		
		if(browser.equalsIgnoreCase("aws")) {
			 String myProjectARN = "arn:aws:devicefarm:us-west-2:472842628937:testgrid-project:fc0eb1ca-9f14-4da3-a274-d7b27b0fe64b";
			    DeviceFarmClient client  = DeviceFarmClient.builder().region(Region.US_EAST_1).build();
			    CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder()
			      .expiresInSeconds(300)
			      .projectArn(myProjectARN)
			      .build();
			    CreateTestGridUrlResponse response = client.createTestGridUrl(request);
			    URL testGridUrl = null;
				try {
					testGridUrl = new URL(response.url());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    // You can now pass this URL into RemoteWebDriver.
			     driver = new RemoteWebDriver(testGridUrl, DesiredCapabilities.chrome());
		}
		
		    
		String currentDir=System.getProperty("user.dir")+"/libs/";
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", currentDir+ "chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("Firefox") 
				|| browser.equalsIgnoreCase("ff")) {
			System.setProperty("webdriver.gecko.driver", currentDir+ "geckodriver.exe");
			driver=new FirefoxDriver();
		}

		else if(browser.equalsIgnoreCase("InternetExplorer")
				|| browser.equalsIgnoreCase("Internet Explorer")
				|| browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", currentDir+ "IEDriverServer.exe");
			driver=new InternetExplorerDriver();
		}

		else if(browser.equalsIgnoreCase("edge")
				|| browser.equalsIgnoreCase("Microsoft Edge")
				|| browser.equalsIgnoreCase("MS Edge")) {
			System.setProperty("webdriver.edge.driver", currentDir+ "msedgedriver.exe");
			driver=new EdgeDriver();
		}

		driver.get(url);
		driver.manage().window().maximize();
	}

	public void goToPage(String url) {
		driver.navigate().to(url);
	}
	/**
	 * implicit wait
	 */
	public void implicitLoadTime() {
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
	}

	/**
	 * page load wait
	 */
	public void pageLoadTime() {
		driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
	}

	/**
	 * find web element with xpath locator
	 * 
	 * @param xpathExpression
	 * @return web element
	 */
	public WebElement findXpath(String xpathExpression) {
		return driver.findElement(By.xpath(xpathExpression));
	}

	/**
	 * find list of web elements with xpath locator
	 * 
	 * @param xpathExpression
	 * @return
	 */
	public List<WebElement> findWebElementListByXpath(String xpathExpression) {
		return driver.findElements(By.xpath(xpathExpression));
	}

	/**
	 * find element with id locator
	 * 
	 * @param id
	 * @return web element
	 */
	public WebElement findId(String id) {
		return driver.findElement(By.id(id));
	}


	public static void sleep() throws InterruptedException {
		Thread.sleep(2000);
	}

	/**
	 * Mouse Hover and click on element
	 * 
	 * @param Mouse
	 *            Hover element
	 * @param Element
	 *            on which action need to be performed
	 * @return void
	 */
	public void mouseHoverAndClick(WebElement elementmouseHover, WebElement elementToPerformActions) {
		Actions action = new Actions(driver);
		action.moveToElement(elementmouseHover).moveToElement(elementToPerformActions).click().perform();
	}

	public void mouseOverActionDoubleclick(WebElement we, WebElement src) {
		Actions action = new Actions(driver);
		action.moveToElement(we).doubleClick(src).perform();

	}

	public void mouseOverAction(WebElement we) {
		Actions action = new Actions(driver);
		action.moveToElement(we).perform();
	}

	public void mouseOverAndClick(WebElement we) {
		Actions action = new Actions(driver);
		action.moveToElement(we).click().perform();
	}

	public void mouseOverAndDoubleClick(WebElement we) {
		Actions action = new Actions(driver);
		action.moveToElement(we).doubleClick().perform();
	}

	/**
	 * Perform keyboard action
	 * 
	 * @param key
	 * @param symbol
	 */
	public void keyBoard(Keys key, String symbol) {
		Actions act = new Actions(driver);
		act.sendKeys(Keys.chord(key, symbol)).perform();

	}

	public void sendKeysKeyboard(WebElement element, String searchText) {
		element.clear();
		Actions act = new Actions(driver);
		act.sendKeys(element, searchText).perform();
	}

	public void performKeyboardActions(WebElement element , Keys key) {
		Actions act = new Actions(driver);
		act.sendKeys(element, key).build().perform();
	}

	public void clearField(WebElement element) {
		element.clear();
	}
	/**
	 * perform right click on any element
	 * 
	 * @param we
	 */
	public void rightClick(WebElement we) {
		Actions act = new Actions(driver);
		act.contextClick(we).perform();
	}

	/**
	 * webDriver wait for linkText locator
	 * 
	 * @param linkName
	 */
	public void waitForLinkPresent(String linkName) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkName)));
	}

	/**
	 * webDriver wait for xpath locator
	 * 
	 * @param xpathExpression
	 */
	public void waitForXpathElementPresent(String xpathExpression) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));

	}

	/**
	 * Switch to the alert and accept the alert
	 */
	public void acceptAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.alertIsPresent()).accept();;
	}

	/**
	 * Switch to the alert and cancel the alert
	 */
	public void cancelAlert() {
		Alert alt = driver.switchTo().alert();
		alt.getText();
		alt.dismiss();
	}

	/**
	 * Select a value(visibleText) from the drop down list using select class
	 * 
	 * @param xpathExpression
	 * @param visibleText
	 */
	public void select(String xpathExpression, String visibleText) {
		Select sel = new Select(driver.findElement(By.xpath(xpathExpression)));
		sel.selectByVisibleText(visibleText);
	}

	/**
	 * Select a value(Index) from the drop down list using select class
	 * 
	 * @param xpathExpression
	 * @param index
	 */
	public void select(String xpathExpression, int index) {
		Select sel = new Select(driver.findElement(By.xpath(xpathExpression)));
		sel.selectByIndex(index);
	}

	/**
	 * Get the text of the webelement
	 * 
	 * @param element
	 */
	public String getText(WebElement element) {
		String sElement = element.getText();
		System.out.println(element.getText());
		return sElement;
	}

	/**
	 * Getting current location of the project
	 * 
	 * @return getSystemProperties
	 */
	public static String getSystemProperties() {
		String getSystemProperty = System.getProperty("user.dir");
		return getSystemProperty;
	}

	public void switchToIFrame(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
		//driver.switchTo().frame(element);
	}

	/**
	 * Switch to Iframe by index
	 * 
	 * @param i
	 */
	public void switchToIFrameIndex(int i) {
		driver.switchTo().frame(i);
	}

	public void changeColor(String id, String color) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.getElementById('" + id + "').setAttribute('value', '" + color + "')");
		// WebElement element = (WebElement)
		// executor.executeScript("document.getElementById('"+id+"').setAttribute('value',
		// '" +"#"+color+"')");
		// System.out.println(element);
	}

	public WebElement fluentWait(WebElement element) {
		@SuppressWarnings("deprecation")
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		return element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(By.xpath("//*[@id='softwareTestingMaterial']"));
				String getTextOnPage = element.getText();
				if(getTextOnPage.equals("Software Testing Material - DEMO PAGE")){
					System.out.println(getTextOnPage);
					return element;
				}else{
					System.out.println("FluentWait Failed");
					return null;
				}
			}
		});

	}

	public void jsClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", element);
	}

	public void findElementAndClick(WebElement element) {
		/*
		 * new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(100))
		 * .pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class);
		 */
		new WebDriverWait(driver, explicitWait)
		.withTimeout(Duration.ofSeconds(explicitWait))
		.pollingEvery(Duration.ofSeconds(1))
		.until(ExpectedConditions.elementToBeClickable(element));
		jsClick(element);
	}


	public void findElementAndClick1(WebElement element) {
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.elementToBeClickable(element)).click();
	}

	public WebElement findByXpath(String xpathExpression) {
		return driver.findElement(By.xpath(xpathExpression));
	}

	public WebElement findById(String id) {
		return driver.findElement(By.id(id));
	}

	public void sleep(int milliSeconds) throws InterruptedException {
		Thread.sleep(milliSeconds);
	}

	public void waitForElementPresent(By element) {
		WebDriverWait wait = new WebDriverWait(driver, explicitWait);
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}

	public void waitForElementPresent(WebElement element, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementPresent(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, explicitWait);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public void waitForElementClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, explicitWait);
		wait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void selectByText(WebElement element, String visibleText) {
		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
	}

	public void selectByText(By byExpression, String visibleText) {
		Select sel = new Select(findByXpath(byExpression));
		sel.selectByVisibleText(visibleText);
	}

	public void selectByIndex(String xpathExpression, int index) {
		Select sel = new Select(driver.findElement(By.xpath(xpathExpression)));
		sel.selectByIndex(index);
	}

	public void selectByIndex(WebElement element, int index) {
		Select sel = new Select(element);
		sel.selectByIndex(index);
	}

	public ArrayList<String> getAllDrpDwnOptions(WebElement element){
		ArrayList<String> listOfOptions = new ArrayList<String>();
		List<WebElement> options=  new Select(element).getOptions();
		for (WebElement option:options) {
			listOfOptions.add(option.getText().trim());
		}
		return listOfOptions;

	}
	public static String getSystemProperties(String propName) {
		return System.getProperty(propName);
	}

	public void switchToIFrame(String element) {
		driver.switchTo().frame(element);
	}

	public void scrollToBottomInsideDiv(WebElement scrollArea) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight)", scrollArea);
	}

	public void scrollDownJavaScript() {
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollUPJavaScript() {
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	public void scrollByXYPosition(String Xposition, String Yposition) {
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("window.scrollTo(" + Xposition + "," + Yposition + ")");
	}

	public void scrollToElement(WebElement webElement) {
		JavascriptExecutor javascriptExecutor = ((JavascriptExecutor) driver);
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", webElement);

	}

	public WebElement findByXpath(By element) {
		/*
		 * if (waitFor) waitForElementPresent(element);
		 */
		return driver.findElement(element);
	}

	public String getAttribute(WebElement element, String attribute) {

		return element.getAttribute(attribute);
	}

	public void findElementAndSendkey(WebElement element, String elementValue) {
		new WebDriverWait(driver, explicitWait)
		.withTimeout(Duration.ofSeconds(explicitWait))
		.pollingEvery(Duration.ofSeconds(1))
		.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(elementValue);
	}

	public void findElementAndSendkey1(WebElement element, String elementValue) {
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.visibilityOf(element)).sendKeys(elementValue);
	}

	public void type(WebElement element, String value) {
		element.sendKeys(value);
	}
	public void findElementAndSendPassword(WebElement element, String elementValue){
		/*
		 * String pass = null; try { pass = new
		 * String(Base64.getDecoder().decode(elementValue));
		 * 
		 * } catch(Exception e) { PropertyLoader prop = new PropertyLoader(); pass =
		 * Base64.getEncoder().encodeToString(elementValue.getBytes());
		 * prop.writePropertyValue("password", pass); }
		 */
		String pass = new String(Base64.getDecoder().decode(elementValue));
		new WebDriverWait(driver, explicitWait).until(ExpectedConditions.visibilityOf(element)).sendKeys(pass);
	}
	public static String timeStamp(){

		return new SimpleDateFormat("yyMMddHHmmss").format(new Date()); 
	}

	public boolean isElementPresent(By element){
		boolean b = false;

		try{
			driver.findElement(element);
			b = true;
		}

		catch(Exception e){
			b = false;
		}
		return b;
	}

	public int sizeOfElement(By element){
		return driver.findElements(element).size();

	}

	public String getAttributeCss(By element, String cssValue) {

		return driver.findElement(element).getCssValue(cssValue);
	}

	public String getAttributeCss(WebElement element, String cssValue) {

		return element.getCssValue(cssValue);
	}

	public void close() {
		driver.quit();	
	}

	public String randomStringGenerator(int length, boolean letters, boolean numbers) {
		return RandomStringUtils.random(length, letters, numbers);
	}

	public void switchToNextTab1(int i) {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(i));
	}

	public void switchToNextTab() {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		System.out.println(tabs.size());
		driver.switchTo().window(tabs.get(tabs.size()-1));
	}

	public void navigateTo(String url) {
		driver.navigate().to(url);
	}

	public String addingDaysFromToday(int daysToAdd) {
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, daysToAdd);
		dt = c.getTime();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
		String date = simpleDateFormat.format(dt);
		return date;
	}

	public boolean isElementSelected(WebElement element) {
		return element.isSelected();
	}

	public void waitForLoad(long secToWait) throws InterruptedException {
		Thread.sleep(secToWait);
	}

}
