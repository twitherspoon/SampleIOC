package commons;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class Log {

	static ExtentTest test;
	static ExtentReports report;
	static ExtentSparkReporter spark ;
	
	public static String testcaseName = null;
	public static String browser = null;
	public static String environment = null;

	public static void setup(String fileName) {

		htmlReporter = new ExtentHtmlReporter("Reports/"+fileName+"_"+SeleniumHelper.timeStamp()+".html");
		report = new ExtentReports();		
		test = report.createTest(fileName);

	}

	public static void pass(String logMessage) {
		test.log(Status.PASS, logMessage);


	}

	public static void fail(String logMessage) throws IOException {

		String scrnshotpath=capture(SeleniumHelper.driver);
		test.log(Status.FAIL, logMessage,MediaEntityBuilder.createScreenCaptureFromPath(scrnshotpath).build());
	}


	public static void tearDown() {
		report.attachReporter(spark);
		report.flush();
	}


	public static String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("Reports/Screenshots/" + System.currentTimeMillis()
		+ ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;
	}
}
