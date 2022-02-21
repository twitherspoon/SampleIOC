package testScripts;

import java.awt.AWTException;

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

		//db = new DataBaseAccessUtility(prop.getPropertyValue("database"));

		helper = new SeleniumHelper(prop.getPropertyValue("browser"), prop.getPropertyValue("url"));
		helper.initialization();
	}

	@Test
	public void ScheduleManagement() throws InterruptedException, AWTException {
		
		LoginPage login = new LoginPage(helper);
		EMSDashboard dashboard = new EMSDashboard(helper);
		
		login.typeUsername(prop.getPropertyValue("userName"));
		login.typePassword(prop.getPropertyValue("password"));
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
	

	@AfterTest (enabled = false)
	public void after() {
		helper.close();
	}
}
