package com.cms.testcases;

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

public class ChangePasswordTest extends BaseTest {
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

		launchLocalUrl();
		
		Thread.sleep(2000);
		
		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();
		
		Thread.sleep(2000);
		
	   String ActualProfileName=lp.GetProfileName();
	   String ExpectedProfileName ="Welcome, AutomationTesting!";
	   
	   sf.assertEquals(ActualProfileName, ExpectedProfileName);
	   
	   tcp.ClickonChangePassword();
	   
	   tcp.ClickonCurrentPassword();
	   
	   tcp.ClickonNewPassword();
	   
	   tcp.ClickonconfirmPassword();
	   
	   lp.ClickonLogoutBtn();
	   
		lp.SendUserName();

		lp.SendPassword();

		lp.ClickonLoginBtn();
	   
	   
	   sf.assertAll();
		
	}
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



