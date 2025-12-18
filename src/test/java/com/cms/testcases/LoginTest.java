package com.cms.testcases;

import com.cms.basetest.BaseTest;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cms.basetest.BaseTest;
import com.cms.utility.Utility;

public class LoginTest extends BaseTest {
	public SoftAssert sf;
	public JavascriptExecutor js;


	@BeforeClass
	@Parameters("browser")
	public void openUrl(String browser) throws IOException
	{
		initBrowser(browser);
		creatingObject();	
		//		Utility.showTooltip("Browser is Launched using Selenium Automation Tool");
		sf = new SoftAssert();
	}

	//	@BeforeMethod
	public void LaunchUrl() throws IOException, InterruptedException
	{
		//		Thread.sleep(2000);
		launchLocalUrl();
	}

	@Test(priority=1)
	public void ValidateLoginFunctionality() throws InterruptedException, IOException
	{
//		while(true)
//		{
			launchLocalUrl();

			Thread.sleep(2000);

			lp.SendUserName();

			lp.SendPassword();

			lp.ClickonLoginBtn();

			Thread.sleep(2000);

//			System.out.println("Log4j2 Config Location: " + 
//					System.getProperty("log4j.configurationFile"));
//			System.out.println("Log File Name: " + 
//					System.getProperty("logFilename"));


			String ActualProfileName=lp.GetProfileName();
			String ExpectedProfileName ="Welcome, AutomationTesting!";

			sf.assertEquals(ActualProfileName, ExpectedProfileName);

			lp.ClickonLogoutBtn();

			sf.assertAll();
		}

//	}
	//	@AfterMethod
	public void closeURL()
	{
		driverR.navigate().to("about:blank");
	}

	@AfterClass
	public void closebrowser()
	{

		teardown();
	}

}
