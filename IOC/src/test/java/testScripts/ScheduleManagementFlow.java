package testScripts;

import java.awt.AWTException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import commons.DataBaseAccessUtility;
import commons.PropertyLoader;
import commons.SeleniumHelper;
import pages.EMSDashboard;
import pages.LoginPage;

public class ScheduleManagementFlow {

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
	public void ApplyLicenseForBusiness() throws InterruptedException, AWTException {
		
		LoginPage login = new LoginPage(helper);
		EMSDashboard dashboard = new EMSDashboard(helper);
		
		login.typeUsername(prop.getPropertyValue("userName"));
		login.typePassword(prop.getPropertyValue("password"));
		login.clickLoginBtn();
		
		helper.navigateTo(prop.getPropertyValue("urlEMSPortal"));
		
		if(dashboard.dashboardPageLoad()) {
			
			dashboard.clickSportsNavLink();
			if(dashboard.sportsSectionLoad()) {
				
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
