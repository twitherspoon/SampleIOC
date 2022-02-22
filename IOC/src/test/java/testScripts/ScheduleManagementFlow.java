package testScripts;

import java.awt.AWTException;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import commons.DataBaseAccessUtility;
import commons.Log;
import commons.PropertyLoader;
import commons.SeleniumHelper;
import pages.EMSDashboard;
import pages.LoginPage;

public class ScheduleManagementFlow extends Log{

	PropertyLoader prop = new PropertyLoader("constants.properties");
	SeleniumHelper helper;
	DataBaseAccessUtility db;

	@BeforeTest
	public void before() {

		db = new DataBaseAccessUtility(prop.getPropertyValue("database"));

		helper = new SeleniumHelper(prop.getPropertyValue("browser"), prop.getPropertyValue("url"));
		helper.initialization();
		Log.setup(this.getClass().getSimpleName());
	}

	@Test
	public void ScheduleManagement() throws InterruptedException, AWTException, IOException {
		
		LoginPage login = new LoginPage(helper);
		EMSDashboard dashboard = new EMSDashboard(helper);
		
		String queryDetails = "select * from Creds";
		HashMap<String,String> creds = db.readAccessDB(queryDetails);
		login.typeUsername(creds.get("Username"));
		login.typePassword(creds.get("Password"));
		login.clickLoginBtn();
		
		helper.navigateTo(prop.getPropertyValue("urlEMSPortal"));
		
		if(dashboard.dashboardPageLoad()) {
			Log.fail("The dashboard did not load properly!");
			dashboard.clickSportsNavLink();
			if(dashboard.sportsSectionLoad()) {
				Log.pass("The sports section on the dashboard loaded successfully!");
			}
			else {
				System.err.println("Sports section didn't load!!");
			}
		}
		else {
			System.err.println("Dashboard didn't load!!");
		}
	}
	

	@AfterTest (enabled = true)
	public void after() {
		helper.close();
		Log.tearDown();
	}
}
