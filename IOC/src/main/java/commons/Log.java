package commons;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;


public class Log {

	static ExtentTest test;
	static ExtentReports report;
	static ExtentHtmlReporter htmlReporter ;

	public static String testcaseName = null;
	public static String browser = null;
	public static String environment = null;
	static String concatenate=".";
	
	public static void setup(String fileName) {

		htmlReporter = new ExtentHtmlReporter("Reports/"+fileName+"_"+SeleniumHelper.timeStamp()+".html");
		//htmlReporter = new ExtentHtmlReporter("Reports/index.html");

		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		test = report.createTest(fileName);
		htmlReporter.config().setReportName("International Olympic Comittee");
		htmlReporter.config().setDocumentTitle("Automation report IOC");

	}

	public static void pass(String logMessage) {
		test.log(Status.PASS, logMessage);


	}

	public static void fail(String logMessage) throws IOException {

		
		String scrnshotpath=null;
		if(AppiumHelper.driver!=null && ((RemoteWebDriver)AppiumHelper.driver).getSessionId()!=null)
		{
		scrnshotpath=capture(AppiumHelper.driver);
		}

		if(SeleniumHelper.driver!=null && ((RemoteWebDriver)SeleniumHelper.driver).getSessionId()!=null)
		{
		scrnshotpath=capture(SeleniumHelper.driver);
		}
		
		scrnshotpath=concatenate+scrnshotpath;
		test.log(Status.FAIL, logMessage,MediaEntityBuilder.createScreenCaptureFromPath(scrnshotpath).build());
	}


	public static void tearDown() {

		report.flush();
	}


	public static String capture(AppiumDriver<MobileElement> driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String errflpath="./Reports/Screenshots/" + System.currentTimeMillis()+ ".png";
		File Dest=new File(errflpath);
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}

	public static String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String errflpath="./Reports/Screenshots/" + System.currentTimeMillis()+ ".png";
		File Dest=new File(errflpath);
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}
}
