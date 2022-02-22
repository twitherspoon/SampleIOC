package commons;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class Log {

	static ExtentTest test;
	static ExtentReports report;
	ExtentSparkReporter spark ;

	@BeforeSuite
	public void beforeSuite() {
		
		spark = new ExtentSparkReporter("target/Spark/Spark.html");
		report = new ExtentReports();
		test = report.createTest("MyFirstTest");
	}

	public static void pass(String logMessage) {
		test.log(Status.PASS, logMessage);

	}

	public static void fail(String logMessage) {
		test.log(Status.FAIL, logMessage);
		//test.log(Status.FAIL,test.addScreenCapture(capture(driver))+ "Test Failed");
		// test.addScreenCaptureFromPath("target/Spark/extent.png").fail(MediaEntityBuilder.createScreenCaptureFromPath("target/Spark/extent.png").build());

	}

	@AfterSuite
	public void afterSuite() {
		report.attachReporter(spark);
		report.flush();
	}
	
	
	public static String capture(WebDriver driver) throws IOException {
	File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	File Dest = new File("src/../BStackImages/" + System.currentTimeMillis()
	+ ".png");
	String errflpath = Dest.getAbsolutePath();
	FileUtils.copyFile(scrFile, Dest);
	return errflpath;
	}
}
